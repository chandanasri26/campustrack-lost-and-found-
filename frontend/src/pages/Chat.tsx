import { useState, useEffect } from "react";
import { useLocation } from "wouter";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { Badge } from "@/components/ui/badge";
import { MessageSquare, Send, ArrowLeft, Check, CheckCheck } from "lucide-react";
import { messageApi } from "@/lib/api";
import { Conversation, Message } from "@/types";
import { useAuth } from "@/contexts/AuthContext";

export default function Chat() {
    const { user } = useAuth();
    const [location] = useLocation();
    const [conversations, setConversations] = useState<Conversation[]>([]);
    const [selectedConversation, setSelectedConversation] = useState<Conversation | null>(null);
    const [chatMessages, setChatMessages] = useState<Message[]>([]);
    const [newMessage, setNewMessage] = useState("");
    const [loading, setLoading] = useState(false);
    const [refreshing, setRefreshing] = useState(false);

    // Load conversations on mount
    useEffect(() => {
        loadConversations();
        const interval = setInterval(loadConversations, 5000); // Poll every 5 seconds
        return () => clearInterval(interval);
    }, []);

    // Handle URL parameters for direct chat
    useEffect(() => {
        const params = new URLSearchParams(location.split("?")[1] || "");
        const itemId = params.get("itemId");
        const receiverId = params.get("receiverId");
        const receiverName = params.get("receiverName");
        const itemTitle = params.get("itemTitle");
        const itemCategory = params.get("itemCategory");
        const itemType = params.get("itemType");

        if (itemId && receiverId && user) {
            const conv: Conversation = {
                itemId,
                itemTitle: itemTitle || "Conversation",
                itemType: itemType || "lost",
                itemCategory: itemCategory || "Unknown",
                otherUserId: receiverId,
                otherUserName: receiverName || "Partner",
                lastMessage: "",
                unreadCount: 0,
                updatedAt: new Date().toISOString(),
            };
            setSelectedConversation(conv);
            loadChatMessages(itemId);
        }
    }, [location, user]);

    const loadConversations = async () => {
        try {
            setRefreshing(true);
            const response = await messageApi.getConversations();
            setConversations(response.data);
        } catch (error) {
            console.error("Failed to load conversations:", error);
        } finally {
            setRefreshing(false);
        }
    };

    const loadChatMessages = async (itemId: string) => {
        try {
            const response = await messageApi.getMessagesForItem(itemId);
            setChatMessages(response.data);
        } catch (error) {
            console.error("Failed to load chat messages:", error);
        }
    };

    const openConversation = async (conversation: Conversation) => {
        setSelectedConversation(conversation);
        try {
            await messageApi.markRead(conversation.itemId);
        } catch (error) {
            console.error("Failed to mark as read:", error);
        }
        await loadChatMessages(conversation.itemId);
        await loadConversations();
    };

    const handleSendMessage = async () => {
        if (!newMessage.trim() || !selectedConversation || !user) return;

        setLoading(true);
        try {
            await messageApi.sendMessage({
                itemId: selectedConversation.itemId,
                receiverId: selectedConversation.otherUserId,
                content: newMessage.trim(),
            });
            setNewMessage("");
            await loadChatMessages(selectedConversation.itemId);
            await loadConversations();
        } catch (error) {
            console.error("Failed to send message:", error);
        } finally {
            setLoading(false);
        }
    };

    const formatTime = (dateString: string) => {
        const date = new Date(dateString);
        return date.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });
    };

    const formatDate = (dateString: string) => new Date(dateString).toLocaleString();

    return (
        <div className="min-h-screen bg-slate-50">
            <main className="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
                <div className="mb-4 flex items-center gap-2">
                    <MessageSquare className="h-5 w-5 text-sky-600" />
                    <h1 className="text-xl font-semibold text-slate-900">Messages</h1>
                </div>

                <div className="grid grid-cols-1 gap-4 lg:grid-cols-[320px_1fr]">
                    {/* Conversations Sidebar */}
                    <div className="rounded-lg border border-slate-200 bg-white shadow-sm overflow-hidden">
                        <div className="border-b border-slate-200 bg-gradient-to-r from-sky-50 to-blue-50 p-4">
                            <h2 className="text-sm font-semibold text-slate-900">Conversations</h2>
                        </div>
                        <div className="max-h-[700px] overflow-y-auto">
                            {conversations.length === 0 ? (
                                <div className="p-4 text-center">
                                    <p className="text-xs text-slate-500">No conversations yet</p>
                                </div>
                            ) : (
                                conversations.map((conversation) => (
                                    <div
                                        key={`${conversation.itemId}-${conversation.otherUserId}`}
                                        onClick={() => openConversation(conversation)}
                                        className={`border-b border-slate-100 px-4 py-3 cursor-pointer transition ${
                                            selectedConversation?.itemId === conversation.itemId &&
                                            selectedConversation?.otherUserId === conversation.otherUserId
                                                ? "bg-sky-50"
                                                : "hover:bg-slate-50"
                                        }`}
                                    >
                                        <div className="flex items-start justify-between gap-2 mb-1">
                                            <p className="text-sm font-semibold text-slate-900 truncate">
                                                {conversation.otherUserName}
                                            </p>
                                            {conversation.unreadCount > 0 && (
                                                <span className="inline-flex items-center justify-center h-5 w-5 rounded-full bg-sky-600 text-[10px] font-bold text-white">
                                                    {conversation.unreadCount}
                                                </span>
                                            )}
                                        </div>
                                        <p className="text-xs text-slate-500 truncate mb-1">{conversation.itemTitle}</p>
                                        <p className="text-xs text-slate-600 line-clamp-1">{conversation.lastMessage || "No messages yet"}</p>
                                    </div>
                                ))
                            )}
                        </div>
                    </div>

                    {/* Chat Area */}
                    <div className="rounded-lg border border-slate-200 bg-white shadow-sm overflow-hidden flex flex-col h-[700px]">
                        {selectedConversation ? (
                            <>
                                {/* Chat Header */}
                                <div className="border-b border-slate-200 bg-gradient-to-r from-sky-50 to-blue-50 px-6 py-4 flex items-center justify-between">
                                    <div>
                                        <h3 className="font-semibold text-slate-900">{selectedConversation.otherUserName}</h3>
                                        <p className="text-xs text-slate-500">
                                            {selectedConversation.itemType === "lost" ? "Lost" : "Found"} • {selectedConversation.itemCategory}
                                        </p>
                                    </div>
                                </div>

                                {/* Messages */}
                                <div className="flex-1 overflow-y-auto bg-white p-6 space-y-4">
                                    {chatMessages.length === 0 ? (
                                        <div className="flex h-full items-center justify-center">
                                            <p className="text-sm text-slate-500">Start the conversation by sending a message</p>
                                        </div>
                                    ) : (
                                        chatMessages.map((message) => (
                                            <div
                                                key={message.id}
                                                className={`flex ${message.senderId === user?.id ? "justify-end" : "justify-start"}`}
                                            >
                                                <div
                                                    className={`flex max-w-xs gap-2 items-end ${
                                                        message.senderId === user?.id ? "flex-row-reverse" : ""
                                                    }`}
                                                >
                                                    <div
                                                        className={`rounded-2xl px-4 py-2 ${
                                                            message.senderId === user?.id
                                                                ? "bg-sky-600 text-white"
                                                                : "bg-slate-100 text-slate-900"
                                                        }`}
                                                    >
                                                        <p className="text-sm break-words">{message.content}</p>
                                                    </div>
                                                    <div className="flex flex-col items-center gap-1">
                                                        <span className="text-[11px] text-slate-500">
                                                            {formatTime(message.createdAt)}
                                                        </span>
                                                        {message.senderId === user?.id && (
                                                            <CheckCheck className="h-3.5 w-3.5 text-sky-600" />
                                                        )}
                                                    </div>
                                                </div>
                                            </div>
                                        ))
                                    )}
                                </div>

                                {/* Message Input */}
                                <div className="border-t border-slate-200 bg-white p-4">
                                    <div className="flex gap-2">
                                        <Textarea
                                            placeholder="Type a message..."
                                            value={newMessage}
                                            onChange={(e) => setNewMessage(e.target.value)}
                                            onKeyDown={(e) => {
                                                if (e.key === "Enter" && !e.shiftKey) {
                                                    e.preventDefault();
                                                    handleSendMessage();
                                                }
                                            }}
                                            className="resize-none"
                                            rows={2}
                                        />
                                        <Button
                                            onClick={handleSendMessage}
                                            disabled={!newMessage.trim() || loading}
                                            size="icon"
                                            className="h-10 w-10 bg-sky-600 hover:bg-sky-700 shrink-0"
                                        >
                                            <Send className="h-4 w-4" />
                                        </Button>
                                    </div>
                                </div>
                            </>
                        ) : (
                            <div className="flex h-full items-center justify-center">
                                <div className="text-center">
                                    <MessageSquare className="h-16 w-16 text-slate-300 mx-auto mb-4" />
                                    <h3 className="text-lg font-medium text-slate-900 mb-2">Select a conversation</h3>
                                    <p className="text-sm text-slate-600">Choose a conversation from the left or message an item owner</p>
                                </div>
                            </div>
                        )}
                    </div>
                </div>
            </main>
        </div>
    );
}
