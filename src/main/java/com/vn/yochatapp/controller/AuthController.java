package com.vn.yochatapp.controller;

import com.vn.yochatapp.Constants;
import com.vn.yochatapp.config.security.jwt.JwtUtils;
import com.vn.yochatapp.config.security.service.UserDetailsImpl;
import com.vn.yochatapp.entities.AuthUser;
import com.vn.yochatapp.entities.Role;
import com.vn.yochatapp.model.CommonResponse;
import com.vn.yochatapp.model.LoginRequest;
import com.vn.yochatapp.model.SignupRequest;
import com.vn.yochatapp.model.AuthUserResponse;
import com.vn.yochatapp.service.AuthRoleService;
import com.vn.yochatapp.service.AuthUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthUserService authUserService;

    @Autowired
    AuthRoleService authRoleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @Value("${store-folder}")
    private String storeFolder;
    @Value("${store-url}")
    private String storeUrl;
    @Value("${default-avatar}")
    private String defaultAvatar;
    @PostMapping("/login")
    public ResponseEntity<CommonResponse> login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        CommonResponse commonResponse = new CommonResponse();

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if(authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);

                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

                String jwtCookie = jwtUtils.generateJwtToken(userDetails);

                List<String> roles = userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList());


                AuthUserResponse userInfo = new AuthUserResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        roles,
                        jwtCookie);
                commonResponse.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                commonResponse.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
                commonResponse.setOutput(userInfo);

                return ResponseEntity.ok()
                        .body(commonResponse);
            } else {
                commonResponse.setStatusCode(Constants.RestApiReturnCode.UNAUTHORIZED);
                commonResponse.setMessage(Constants.RestApiReturnCode.UNAUTHORIZED_TXT);
                return ResponseEntity.ok().body(commonResponse);
            }
        } catch (Exception e) {
            logger.error("", e);
            commonResponse.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            commonResponse.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            return ResponseEntity.ok().body(commonResponse);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse> registerUser(@ModelAttribute SignupRequest reqBody) {
        CommonResponse commonResponse = new CommonResponse();
        try {

            if (authUserService.existsByUsername(reqBody.getUsername())) {
                commonResponse.setStatusCode(Constants.RestApiReturnCode.BAD_REQUEST);
                commonResponse.setMessage(Constants.RestApiReturnCode.BAD_REQUEST_TXT);
                commonResponse.setOutput(null);
                commonResponse.setError("Username đã tồn tại!");
                return ResponseEntity.badRequest().body(commonResponse);
            } else {
                AuthUser authUser = new AuthUser(
                        reqBody.getUsername(),
                        reqBody.getFirstName(),
                        reqBody.getLastName(),
                        reqBody.getGender(),
                        passwordEncoder.encode(reqBody.getPassword())
                );
                List<Role> roles = new ArrayList<>();
                Role role = authRoleService.findOne(Constants.AuthRoles.ROLE_USER_ID);
                roles.add(role);
                authUser.setAuthRoles(roles);
                authUserService.create(authUser);
                //save avatar
                if (reqBody.getAvatar() != null) {
                    String fileName = "avatar"+authUser.getId().toString()+reqBody.getAvatar().getOriginalFilename();
                    String uploadDir = storeFolder + fileName;
                    String pathUrl= storeUrl + fileName;

                    File dest = new File(uploadDir);
                    reqBody.getAvatar().transferTo(dest);
                    authUser.setAvatarUrl(pathUrl);
                } else {
                    authUser.setAvatarUrl(defaultAvatar);
                }
                authUserService.update(authUser);

                commonResponse.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
                commonResponse.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
                commonResponse.setError("Đăng ký thành công");
                commonResponse.setOutput(null);
                return ResponseEntity.ok().body(commonResponse);
            }
        } catch (Exception e) {
            logger.error("", e);
            commonResponse.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            commonResponse.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            commonResponse.setError("Lỗi hệ thống");
            return ResponseEntity.ok().body(commonResponse);
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<CommonResponse> logout() {
        CommonResponse commonResponse = new CommonResponse();
        try {
            ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
            commonResponse.setStatusCode(Constants.RestApiReturnCode.SUCCESS);
            commonResponse.setMessage(Constants.RestApiReturnCode.SUCCESS_TXT);
            commonResponse.setError("Đăng xuất thành công");
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(commonResponse);
        } catch (Exception e) {
            logger.error("", e);
            commonResponse.setStatusCode(Constants.RestApiReturnCode.SYS_ERROR);
            commonResponse.setMessage(Constants.RestApiReturnCode.SYS_ERROR_TXT);
            commonResponse.setError("Lỗi hệ thống");
            return ResponseEntity.ok().body(commonResponse);
        }
    }
}
