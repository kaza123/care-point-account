(function () {
    var service = function (systemConfig, $http) {

        //login
        this.login = function (credentials) {
            var headers =
                    credentials
                    ? {authorization: "Basic " + btoa(credentials.username + ":" + credentials.password)}
            : {};

            return  $http.get(systemConfig.apiUrl + "/security/login",
                    {headers: headers});
        };

        //logout
        this.logout = function () {
            return $http.get(systemConfig.apiUrl + "/security/logout");
        };
        //permission list
        this.getViewTrue = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/account/account-setting/user-permission/by-view-true");
        };
        //permission list
        this.getRequiredField = function () {
            return $http.get(systemConfig.apiUrl + "/api/care-point/master/user/get-required-field");
        };

        //ping
        this.ping = function () {
            var headers = {
                "X-Requested-With": "XMLHttpRequest",
                "Access-Control-Allow-Origin": "*"
            };

            return $http.get(systemConfig.apiUrl + "/api/v1/system/environment/ping",
                    {"headers": headers});
        };

    };

    angular.module("appModule")
            .service("SecurityService", service);
}());