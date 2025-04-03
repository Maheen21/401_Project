package com.dishcraft.model;

/**
 * Role enum defines available user roles in the system
 * Each role represents a different level of access and permissions
 */
public enum Role {
    USER,       // Regular user with basic permissions
    ADMIN,      // Administrator with elevated permissions
    ROOT        // Super-admin with full system access
}