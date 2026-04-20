export interface User {
    id: string;
    name: string;
    email: string;
    studentId: string;
    role: "student" | "admin";
    isBlocked: boolean;
    createdAt: string;
}

export interface Item {
    id: string;
    title: string;
    description: string;
    category: string;
    type: "lost" | "found";
    status: "open" | "resolved";
    approved: boolean;
    location: string;
    imageUrl: string | null;
    userId: string;
    userName: string;
    userEmail: string;
    createdAt: string;
    updatedAt: string;
}

export interface Stats {
    total: number;
    lost: number;
    found: number;
    open: number;
    resolved: number;
    byCategory: { category: string; count: number }[];
}

export interface AdminStats {
    totalUsers: number;
    totalItems: number;
    lostItems: number;
    foundItems: number;
    resolvedItems: number;
    openItems: number;
    blockedUsers: number;
    recentActivity: number;
}

export interface Message {
    id: string;
    itemId: string;
    senderId: string;
    receiverId: string;
    content: string;
    read: boolean;
    createdAt: string;
}

export interface Conversation {
    itemId: string;
    itemTitle: string;
    itemType: string;
    itemCategory: string;
    otherUserId: string;
    otherUserName: string;
    lastMessage: string;
    unreadCount: number;
    updatedAt: string;
}

export interface CreateMessageRequest {
    itemId: string;
    receiverId: string;
    content: string;
}
