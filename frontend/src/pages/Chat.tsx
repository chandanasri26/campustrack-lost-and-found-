import { useState, useEffect, useRef } from "react";
import { useLocation } from "wouter";
import { 
    MessageSquare, 
    Send, 
    ArrowLeft, 
    CheckCheck, 
    Smile, 
    MoreVertical, 
    Search, 
    Plus 
} from "lucide-react";
import { messageApi } from "@/lib/api";
import { Conversation, Message } from "@/types";
import { useAuth } from "@/contexts/AuthContext";
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { useToast } from "@/hooks/use-toast";

export default function Chat() {
    const { user } = useAuth();
    const [location, navigate] = useLocation();
    const { toast } = useToast();
    const [conversations, setConversations] = useState<Conversation[]>([]);
    const [selectedConversation, setSelectedConversation] = useState<Conversation | null>(null);
    const [chatMessages, setChatMessages] = useState<Message[]>([]);
    const [newMessage, setNewMessage] = useState("");
    const [loading, setLoading] = useState(false);
    const [, setRefreshing] = useState(false);
    const [searchQuery, setSearchQuery] = useState("");
    const [msgSearchQuery, setMsgSearchQuery] = useState("");
    const [showMsgSearch, setShowMsgSearch] = useState(false);
    const [showChatMobile, setShowChatMobile] = useState(false);
    const messagesEndRef = useRef<HTMLDivElement | null>(null);

    // Load conversations on mount
    useEffect(() => {
        loadConversations();
        const interval = setInterval(loadConversations, 5000); // Poll every 5 seconds
        return () => clearInterval(interval);
    }, []);

    // Handle URL parameters for direct chat
    useEffect(() => {
        const params = new URLSearchParams(window.location.search);
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
            setShowChatMobile(true);
        }
    }, [location, window.location.search, user]);

    // Scroll to bottom on new messages
    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    };

    useEffect(() => {
        if (chatMessages.length > 0) {
            scrollToBottom();
        }
    }, [chatMessages]);

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
        setShowChatMobile(true);
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

    const hasSelectedInList = conversations.some(c => c.itemId === selectedConversation?.itemId);
    let displayConversations = [...conversations];
    if (selectedConversation && !hasSelectedInList) {
        displayConversations = [selectedConversation, ...conversations];
    }

    const filteredConversations = displayConversations.filter(c => 
        c.otherUserName.toLowerCase().includes(searchQuery.toLowerCase()) ||
        c.itemTitle.toLowerCase().includes(searchQuery.toLowerCase())
    );

    const filteredMessages = chatMessages.filter(m => 
        m.content.toLowerCase().includes(msgSearchQuery.toLowerCase())
    );

    return (
        <div className="min-h-screen bg-[#dadbd3]">
            <main className="mx-auto max-w-7xl h-[calc(100vh-80px)] min-h-[500px] max-h-[850px] p-0 md:p-4">
                <div className="flex h-full w-full bg-white md:rounded-2xl shadow-[0_24px_64px_rgba(0,0,0,0.15)] overflow-hidden border border-slate-200/50">
                    
                    {/* WhatsApp-Style Left Sidebar */}
                    <div className={`${showChatMobile ? "hidden" : "flex"} flex flex-col w-full md:w-[380px] shrink-0 border-r border-[#e9edef] h-full bg-white`}>
                        {/* Sidebar Header */}
                        <div className="h-[60px] bg-[#f0f2f5] px-4 flex items-center justify-between shrink-0 border-b border-[#e9edef]">
                            <div className="flex items-center gap-3">
                                <div className="flex h-10 w-10 items-center justify-center rounded-full bg-[#00a884] text-white font-bold shadow">
                                    {user?.name?.charAt(0).toUpperCase() || "U"}
                                </div>
                                <span className="text-sm font-semibold text-slate-800 hidden sm:inline">Chats</span>
                            </div>
                            <div className="flex items-center gap-4 text-slate-600">
                                <MessageSquare className="h-5 w-5 cursor-pointer hover:text-slate-800 transition" />
                                <MoreVertical className="h-5 w-5 cursor-pointer hover:text-slate-800 transition" />
                            </div>
                        </div>

                        {/* Search / Filter box */}
                        <div className="p-2 bg-white shrink-0 border-b border-[#f0f2f5] flex items-center">
                            <div className="relative flex-1 flex items-center bg-[#f0f2f5] rounded-xl px-3 py-1.5 gap-2 border border-transparent focus-within:border-emerald-500 focus-within:bg-white transition-all">
                                <Search className="h-4 w-4 text-slate-500 shrink-0" />
                                <input 
                                    className="bg-transparent text-xs text-slate-800 outline-none w-full" 
                                    placeholder="Search or start new chat" 
                                    value={searchQuery}
                                    onChange={(e) => setSearchQuery(e.target.value)}
                                />
                            </div>
                        </div>

                        {/* Conversations list */}
                        <div className="flex-1 overflow-y-auto bg-white divide-y divide-[#f0f2f5]">
                            {filteredConversations.length === 0 ? (
                                <div className="p-8 text-center text-slate-500">
                                    <MessageSquare className="h-12 w-12 mx-auto mb-3 text-slate-300" />
                                    <p className="text-sm font-medium">No chats found</p>
                                    <p className="text-xs text-slate-400 mt-1">Search or browse items to start a chat.</p>
                                </div>
                            ) : (
                                filteredConversations.map((conversation) => {
                                    const isSelected = selectedConversation?.itemId === conversation.itemId && 
                                                       selectedConversation?.otherUserId === conversation.otherUserId;
                                    return (
                                        <div
                                            key={`${conversation.itemId}-${conversation.otherUserId}`}
                                            onClick={() => openConversation(conversation)}
                                            className={`px-4 py-3 cursor-pointer flex items-center gap-3 transition ${
                                                isSelected ? "bg-[#f0f2f5]" : "hover:bg-[#f5f6f6]"
                                            }`}
                                        >
                                            <div className="flex h-12 w-12 shrink-0 items-center justify-center rounded-full bg-gradient-to-br from-[#128c7e] to-[#075e54] text-white font-bold text-base shadow">
                                                {conversation.otherUserName.charAt(0).toUpperCase()}
                                            </div>
                                            
                                            <div className="flex-1 min-w-0">
                                                <div className="flex items-center justify-between gap-1 mb-1">
                                                    <h3 className="text-sm font-semibold text-slate-900 truncate">
                                                        {conversation.otherUserName}
                                                    </h3>
                                                    <span className="text-[10px] text-slate-400 shrink-0">
                                                        {formatTime(conversation.updatedAt)}
                                                    </span>
                                                </div>
                                                <p className="text-xs font-medium text-emerald-600 truncate mb-1">
                                                    [{conversation.itemType.toUpperCase()}] {conversation.itemTitle}
                                                </p>
                                                <p className="text-xs text-slate-500 truncate line-clamp-1">
                                                    {conversation.lastMessage || "No messages yet"}
                                                </p>
                                            </div>
                                            
                                            {conversation.unreadCount > 0 && (
                                                <span className="inline-flex h-5 min-w-5 shrink-0 items-center justify-center rounded-full bg-[#25d366] text-[10px] font-bold text-white px-1 shadow">
                                                    {conversation.unreadCount}
                                                </span>
                                            )}
                                        </div>
                                    );
                                })
                            )}
                        </div>
                    </div>

                    {/* WhatsApp-Style Right Chat Pane */}
                    <div className={`${showChatMobile ? "flex" : "hidden md:flex"} flex-1 flex-col h-full bg-[#efeae2] relative`}>
                        {selectedConversation ? (
                            <>
                                {/* Chat Header */}
                                <div className="h-[60px] bg-[#f0f2f5] px-4 flex items-center justify-between shrink-0 border-b border-[#e9edef] shadow-sm z-10">
                                    <div className="flex items-center gap-3 min-w-0">
                                        <button 
                                            className="md:hidden p-1.5 hover:bg-[#e1e3e5] rounded-full transition text-slate-600"
                                            onClick={() => setShowChatMobile(false)}
                                        >
                                            <ArrowLeft className="h-5 w-5" />
                                        </button>
                                        <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-gradient-to-br from-[#128c7e] to-[#075e54] text-white font-bold shadow">
                                            {selectedConversation.otherUserName.charAt(0).toUpperCase()}
                                        </div>
                                        <div className="min-w-0">
                                            <h3 className="text-sm font-semibold text-slate-800 truncate">
                                                {selectedConversation.otherUserName}
                                            </h3>
                                            <p className="text-[11px] text-slate-500 font-medium truncate">
                                                {selectedConversation.itemType === "lost" ? "Lost" : "Found"} • {selectedConversation.itemCategory} • {selectedConversation.itemTitle}
                                            </p>
                                        </div>
                                    </div>
                                    <div className="flex items-center gap-4 text-slate-600">
                                        <button 
                                            onClick={() => {
                                                setShowMsgSearch(!showMsgSearch);
                                                if (showMsgSearch) {
                                                    setMsgSearchQuery("");
                                                }
                                            }}
                                            className={`p-1.5 rounded-full transition outline-none ${showMsgSearch ? "bg-[#e1e3e5] text-slate-800" : "hover:bg-[#e1e3e5] text-slate-600"}`}
                                        >
                                            <Search className="h-5 w-5" />
                                        </button>
                                        <DropdownMenu>
                                            <DropdownMenuTrigger asChild>
                                                <button className="p-1.5 hover:bg-[#e1e3e5] rounded-full transition text-slate-600 outline-none">
                                                    <MoreVertical className="h-5 w-5" />
                                                </button>
                                            </DropdownMenuTrigger>
                                            <DropdownMenuContent className="w-48 bg-white border border-slate-200 shadow-md rounded-xl p-1 z-50">
                                                <DropdownMenuItem 
                                                    onClick={() => {
                                                        setChatMessages([]);
                                                        toast({ title: "Chat cleared locally" });
                                                    }}
                                                    className="px-3 py-2 text-sm text-slate-800 rounded-lg cursor-pointer hover:bg-slate-100 transition"
                                                >
                                                    Clear Chat
                                                </DropdownMenuItem>
                                                <DropdownMenuItem 
                                                    onClick={() => {
                                                        setSelectedConversation(null);
                                                        setShowChatMobile(false);
                                                    }}
                                                    className="px-3 py-2 text-sm text-slate-800 rounded-lg cursor-pointer hover:bg-slate-100 transition"
                                                >
                                                    Close Chat
                                                </DropdownMenuItem>
                                                <DropdownMenuItem 
                                                    onClick={() => {
                                                        if (selectedConversation?.itemId) {
                                                            navigate(`/items/${selectedConversation.itemId}`);
                                                        }
                                                    }}
                                                    className="px-3 py-2 text-sm text-slate-800 rounded-lg cursor-pointer hover:bg-slate-100 transition"
                                                >
                                                    View Item Board
                                                </DropdownMenuItem>
                                            </DropdownMenuContent>
                                        </DropdownMenu>
                                    </div>
                                </div>

                                {showMsgSearch && (
                                    <div className="bg-[#f0f2f5] px-4 py-2 border-b border-[#e9edef] flex items-center justify-between shrink-0 z-10 transition-all">
                                        <div className="relative flex-1 flex items-center bg-white rounded-lg px-3 py-1.5 gap-2 border border-slate-200">
                                            <Search className="h-4 w-4 text-slate-400 shrink-0" />
                                            <input 
                                                className="bg-transparent text-xs text-slate-800 outline-none w-full" 
                                                placeholder="Search messages in this chat" 
                                                value={msgSearchQuery}
                                                onChange={(e) => setMsgSearchQuery(e.target.value)}
                                            />
                                            {msgSearchQuery && (
                                                <button className="text-[10px] text-slate-400 hover:text-slate-600 font-bold" onClick={() => setMsgSearchQuery("")}>✕</button>
                                            )}
                                        </div>
                                    </div>
                                )}

                                {/* Messages Scroll Window */}
                                <div className="flex-1 overflow-y-auto px-6 py-4 space-y-3 flex flex-col bg-[#efeae2]">
                                    {filteredMessages.length === 0 ? (
                                        <div className="flex h-full items-center justify-center">
                                            <p className="text-xs bg-[#ffe596]/80 text-[#60490b] px-3 py-1.5 rounded-lg shadow-sm font-medium">
                                                {msgSearchQuery ? "No matching messages found" : "Start the conversation by sending a message"}
                                            </p>
                                        </div>
                                    ) : (
                                        filteredMessages.map((message) => {
                                            const isMe = message.senderId === user?.id;
                                            return (
                                                <div
                                                    key={message.id}
                                                    className={`flex ${isMe ? "justify-end" : "justify-start"}`}
                                                >
                                                    <div
                                                        className={`rounded-lg px-3 py-1.5 shadow-sm max-w-[65%] text-slate-900 text-sm relative flex flex-col ${
                                                            isMe 
                                                                ? "bg-[#d9fdd3] rounded-tr-none" 
                                                                : "bg-white rounded-tl-none"
                                                        }`}
                                                    >
                                                        <p className="text-sm break-words pr-12">{message.content}</p>
                                                        <div className="absolute bottom-1 right-2 flex items-center gap-1 select-none">
                                                            <span className="text-[9px] text-slate-500">
                                                                {formatTime(message.createdAt)}
                                                            </span>
                                                            {isMe && (
                                                                <CheckCheck className={`h-3.5 w-3.5 ${message.read ? "text-[#53bdeb]" : "text-slate-400"}`} />
                                                            )}
                                                        </div>
                                                    </div>
                                                </div>
                                            );
                                        })
                                    )}
                                    <div ref={messagesEndRef} />
                                </div>

                                {/* Chat Message Input Area */}
                                <div className="bg-[#f0f2f5] px-4 py-2 flex items-center gap-3 shrink-0 border-t border-[#e9edef] z-10">
                                    <div className="flex items-center gap-3 text-slate-600">
                                        <Smile className="h-6 w-6 cursor-pointer hover:text-slate-800 transition" />
                                        <Plus className="h-6 w-6 cursor-pointer hover:text-slate-800 transition" />
                                    </div>
                                    
                                    <input
                                        placeholder="Type a message"
                                        value={newMessage}
                                        onChange={(e) => setNewMessage(e.target.value)}
                                        onKeyDown={(e) => {
                                            if (e.key === "Enter" && !e.shiftKey) {
                                                e.preventDefault();
                                                handleSendMessage();
                                            }
                                        }}
                                        className="flex-1 bg-white text-sm text-slate-800 rounded-xl px-4 py-2.5 outline-none border border-transparent focus:border-slate-200 transition-all placeholder-slate-400"
                                    />
                                    
                                    <button
                                        onClick={handleSendMessage}
                                        disabled={!newMessage.trim() || loading}
                                        className="h-10 w-10 shrink-0 bg-[#00a884] hover:bg-[#008f72] active:scale-95 disabled:scale-100 disabled:opacity-60 disabled:cursor-not-allowed text-white rounded-full flex items-center justify-center shadow transition-all"
                                    >
                                        <Send className="h-4.5 w-4.5 text-white" />
                                    </button>
                                </div>
                            </>
                        ) : (
                            <div className="flex-1 flex flex-col items-center justify-center bg-[#f8f9fa] border-l border-slate-200/50 p-8 text-center h-full">
                                <div className="max-w-md flex flex-col items-center">
                                    <div className="h-32 w-32 rounded-full bg-slate-100 flex items-center justify-center text-[#25d366] mb-6 shadow-sm border border-slate-100">
                                        <MessageSquare className="h-16 w-16" />
                                    </div>
                                    <h3 className="text-xl font-bold text-slate-800 mb-2">CampusFind Web</h3>
                                    <p className="text-sm text-slate-500 mb-6 leading-relaxed">
                                        Send and receive messages for lost and found items on campus. Keep chats connected. Verified reports are safe and trusted.
                                    </p>
                                    <div className="h-px w-full bg-slate-200/80 mb-6"></div>
                                    <p className="text-xs text-slate-400 flex items-center gap-1.5">
                                        🔒 End-to-end encrypted chats
                                    </p>
                                </div>
                            </div>
                        )}
                    </div>

                </div>
            </main>
        </div>
    );
}
