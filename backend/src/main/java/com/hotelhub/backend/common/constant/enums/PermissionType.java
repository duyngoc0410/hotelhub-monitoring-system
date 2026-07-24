package com.hotelhub.backend.common.constant.enums;

public enum PermissionType {

    // =========================
    // USER
    // =========================
    USER_READ,
    USER_CREATE,
    USER_UPDATE,
    USER_DELETE,

    // =========================
    // ROLE
    // =========================
    ROLE_READ,
    ROLE_CREATE,
    ROLE_UPDATE,
    ROLE_DELETE,

    // =========================
    // PERMISSION
    // =========================
    PERMISSION_READ,
    PERMISSION_CREATE,
    PERMISSION_UPDATE,
    PERMISSION_DELETE,

    // =========================
    // HOTEL
    // =========================
    HOTEL_READ,
    HOTEL_CREATE,
    HOTEL_UPDATE,
    HOTEL_DELETE,

    // =========================
    // ROOM
    // =========================
    ROOM_READ,
    ROOM_CREATE,
    ROOM_UPDATE,
    ROOM_DELETE,

    // =========================
    // BOOKING
    // =========================
    BOOKING_READ,
    BOOKING_CREATE,
    BOOKING_UPDATE,
    BOOKING_CANCEL,

    // =========================
    // PAYMENT
    // =========================
    PAYMENT_READ,
    PAYMENT_CREATE,
    PAYMENT_UPDATE,
    PAYMENT_REFUND,

    // =========================
    // REVIEW
    // =========================
    REVIEW_READ,
    REVIEW_CREATE,
    REVIEW_UPDATE,
    REVIEW_DELETE,

    // =========================
    // WISHLIST
    // =========================
    WISHLIST_READ,
    WISHLIST_CREATE,
    WISHLIST_DELETE,

    // =========================
    // NOTIFICATION
    // =========================
    NOTIFICATION_READ,
    NOTIFICATION_CREATE,
    NOTIFICATION_DELETE,

    // =========================
    // DASHBOARD
    // =========================
    DASHBOARD_VIEW,

    // =========================
    // SYSTEM
    // =========================
    SYSTEM_CONFIG,
    SYSTEM_AUDIT
}
