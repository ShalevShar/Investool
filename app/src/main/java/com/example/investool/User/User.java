package com.example.investool.User;

public class User {
    private String username;
    private String avatar;
    private UserId userId;
    private String role;

        public User() {
        }

        public User(String username, String avatar, UserId userId, String role) {
            this.username = username;
            this.avatar = avatar;
            this.userId = userId;
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public UserId getUserId() {
            return userId;
        }

        public void setUserId(UserId userId) {
            this.userId = userId;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", userId=" + userId + // Change to userId.toString() if necessary
                    ", role='" + role + '\'' +
                    '}';
        }
    }