package com.skyvn.ten.bean;

public class StsTokenBean {


    /**
     * endpoint : A7mJWBgGX8a3xDwL2vjq/826RcSAeZjtHvffFZShzU8=
     * accessKeyId : vxAXYABhGiTZt5K/6rMaupMhy/N9JWmZR6OZVo8MSFk=
     * accessKeySecret : Pj4XLIBq7jeXeIDcnsPLGSnnxDEmf+BQnd71a/gqMws=
     * bucket : K5RWc2UTTR42Kwq2LigTNMipHWdzryu9pxbMjQL0kOA=
     * key : So0WHL3HcrxInBWuRZPdKQ==
     */

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucket;
    private String key;
    /**
     * securityToken : e/35SVDsxE1HQXelDH0Xjj5GqWMH0xF7BfxLpWQcpMyXN0c4fMPudnYj1bP6WRb8pfWaGsAEw7ViDWCSMX+LaviFHkWila/oc799SBKmqfW+iDAqx1Ck4bqMFqX7sxyA/duwQ2DO5y1l9c/kVRzuqhrfqYVhd9xW6fIrUH9q+O3uQzCsnE/aVFJ3SsIEV95nWrM27ZOaI0r3XE/3We/aIf70Ph2hZzvTqFAbJt7ivaBGkgEAHYwucD1lpM7gCJM3OyBfMtOzy87UntYmF2O3pfYvO+dJXxluE58P2OKNZtHDO9wF3T9kBUqHQNYNwDp1r9vuZsRKOgUmspDETwWauioydWdMpQ0X4FbpXwr09TdIe3CiTOc5eN0dVdP4TIUey4sL4OhXDGMv98e5u1HqiIi1N9DTzX3K+IEybE/MUPvGdWe+LSt12ucUaq8Zj3Dm94b1TDYC3E6dbzdRDcueQmspzSGjtPNqhlCJLg4rjiozisw/fi8LJs9urOSqqzvN0FD+B9vm1NzfAFmOfhyO1x5Pcp8GdWL1K2vqKu74Ul/x+GY2gqG2QYmVQtb0VdUruVrz/yh0YzihydQzw53bZew8xFLzHLZNGI+Ws6UzPx3tZ432v+rCPzKik0doHe/JMne951t+UoXgGMju6shb9GOI1gQYc6ERyp5Bzjti4jWMMvRJ71/l9G5n8SPgFiuwdAUFjqY74vtR2ZV21MWvqiJ/3TdD98KV47ZQBr6wZPQ8ne0KLduLS+bKeCfbNvn5
     */
    private String securityToken;
    private String httpUrl;
    private String oosUrl;

    public String getOosUrl() {
        return oosUrl;
    }

    public void setOosUrl(String oosUrl) {
        this.oosUrl = oosUrl;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }
}
