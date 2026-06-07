import axios from "axios";

const apiBaseUrl = (import.meta.env.VITE_API_URL ?? "/api").replace(/\/$/, "");

const api = axios.create({
    baseURL: apiBaseUrl,
    headers: { "Content-Type": "application/json" },
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem("jwt_token");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401 || error.response?.status === 403) {
            localStorage.removeItem("jwt_token");
            localStorage.removeItem("user");
            window.location.href = "/login";
        }
        return Promise.reject(error);
    }
);

export const messageApi = {
    sendMessage: (data: { itemId: string; receiverId: string; content: string }) =>
        api.post("/messages", data),
    getInbox: () => api.get("/messages/inbox"),
    getSent: () => api.get("/messages/sent"),
    getMessagesForItem: (itemId: string) => api.get(`/messages/item/${itemId}`),
    getConversations: () => api.get("/messages/conversations"),
    markRead: (itemId: string) => api.patch(`/messages/item/${itemId}/mark-read`),
};

export const itemApi = {
    getItem: (id: string) => api.get(`/items/${id}`),
};

export const verificationApi = {
    submitVerification: (data: {
        matchScore: number;
        livenessPassed: boolean;
        idFaceStatus: string;
        selfieFaceStatus: string;
        imageQualitySummary: string;
    }) => api.post("/verification/submit", data),
};

export default api;
