/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.security;

import com.mac.care_point.master.backup.BackupService;
import com.mac.care_point.master.backup_detail.BackupDetailService;
import com.mac.care_point.master.backup_detail.model.MBackupDetail;
import com.mac.care_point.security.model.MUser;
import com.mac.care_point.zutil.Backup;
import com.mac.care_point.zutil.SecurityUtil;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@CrossOrigin
@RestController
@RequestMapping("/security")
public class SESecurityController {

    @Autowired
    private MUserService userService;

    @Autowired
    private BackupService backupService;

    @Autowired
    private BackupDetailService backupDetailService;

    @Value("${backup-creater}")
    private Boolean backupCreater;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Principal login(Principal principal) {
        System.out.println("backupCreater : "+backupCreater);
        if (backupCreater) {
            String lastBackupDate = backupService.getLastBackupDate();
            MBackupDetail backupDetail = backupDetailService.findAll();

            Backup.startBackup(lastBackupDate, backupDetail);
            backupService.updateNewDate();
        }
        return principal;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Map<String, Object> logout(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> result = new HashMap<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            result.put("status", "success");
        } else {
            result.put("status", "failed");
        }

        return result;
    }

    @RequestMapping(value = "/user-current-branch", method = RequestMethod.GET)
    public Integer getCurrentBranch() {
        return SecurityUtil.getCurrentUser().getBranch();
    }

    @RequestMapping(value = "/user-list", method = RequestMethod.GET)
    public List<MUser> getUsers() {
        return userService.getUsers();
    }

}
