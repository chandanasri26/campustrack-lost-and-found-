import { useAuth } from "@/contexts/AuthContext";
import { useLocation } from "wouter";
import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { messageApi } from "@/lib/api";
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Badge } from "@/components/ui/badge";
import { GraduationCap, ChevronDown, LogOut, LayoutDashboard, PlusCircle, MessageCircle, ShieldCheck, UserRound, Bell, Search } from "lucide-react";

export default function Navbar() {
    const { user, logout, isAdmin } = useAuth();
    const [, navigate] = useLocation();
    const [unreadCount, setUnreadCount] = useState(0);
    const [notifications, setNotifications] = useState<any[]>([]);

    const loadNotifications = () => {
        const list = JSON.parse(localStorage.getItem("notifications_list") || "[]");
        setNotifications(list);
    };

    const handleLogout = () => {
        logout();
        navigate("/login");
    };

    const handleNotificationClick = (notif: any) => {
        const updated = notifications.map((n: any) => 
            n.id === notif.id ? { ...n, read: true } : n
        );
        localStorage.setItem("notifications_list", JSON.stringify(updated));
        setNotifications(updated);
        if (notif.itemId) {
            navigate(`/dashboard`);
        } else {
            navigate("/dashboard");
        }
    };

    const clearNotifications = () => {
        const updated = notifications.map((n: any) => ({ ...n, read: true }));
        localStorage.setItem("notifications_list", JSON.stringify(updated));
        setNotifications(updated);
    };

    useEffect(() => {
        loadNotifications();
        const interval = setInterval(loadNotifications, 3000);
        return () => clearInterval(interval);
    }, []);

    useEffect(() => {
        const loadUnread = async () => {
            if (!user) {
                setUnreadCount(0);
                return;
            }
            try {
                const response = await messageApi.getConversations();
                const totalUnread = response.data.reduce((sum: number, convo: { unreadCount: number }) => sum + convo.unreadCount, 0);
                setUnreadCount(totalUnread);
            } catch (error) {
                console.error("Failed to load unread message count:", error);
            }
        };

        loadUnread();
        const interval = setInterval(loadUnread, 5000);
        return () => clearInterval(interval);
    }, [user]);

    const unreadNotifsCount = notifications.filter((n: any) => !n.read).length;
    const bellCount = unreadCount + unreadNotifsCount;

    return (
        <nav className="sticky top-0 z-50 w-full border-b border-slate-200 bg-white/95 backdrop-blur-lg">
            <div className="mx-auto flex max-w-7xl items-center justify-between gap-4 px-4 py-4 sm:px-6 lg:px-8">
                <div className="flex items-center gap-3 cursor-pointer" onClick={() => navigate("/")}>
                    <div className="flex h-11 w-11 items-center justify-center rounded-2xl bg-sky-600 text-white shadow-lg shadow-sky-500/20">
                        <GraduationCap className="h-5 w-5" />
                    </div>
                    <div className="space-y-0.5">
                        <p className="text-sm font-semibold text-slate-900">CampusFind</p>
                        <p className="text-xs text-slate-500">Lost & Found</p>
                    </div>
                </div>

                <div className="flex-1 flex items-center justify-center gap-4">
                    <label className="flex items-center gap-2 rounded-2xl border border-slate-200 bg-slate-50 px-3 py-2 text-sm text-slate-500 shadow-sm">
                        <Search className="h-4 w-4" />
                        <input className="w-40 bg-transparent outline-none md:w-56" placeholder="Search reports" />
                    </label>
                    <DropdownMenu>
                        <DropdownMenuTrigger asChild>
                            <button className="relative flex h-11 w-11 items-center justify-center rounded-2xl border border-slate-200 bg-white text-slate-600 shadow-sm transition hover:bg-slate-50">
                                <Bell className="h-5 w-5" />
                                {bellCount > 0 && (
                                    <span className="absolute -right-1.5 -top-1 inline-flex h-5 min-w-5 items-center justify-center rounded-full bg-rose-500 px-1 text-[10px] font-semibold text-white">
                                        {bellCount}
                                    </span>
                                )}
                            </button>
                        </DropdownMenuTrigger>
                        <DropdownMenuContent className="w-80 p-2 z-50 bg-white border border-slate-200 rounded-xl shadow-lg">
                            <div className="px-3 py-2 font-semibold text-sm border-b border-slate-100 flex items-center justify-between">
                                <span>Notifications</span>
                                {unreadNotifsCount > 0 && (
                                    <button 
                                        onClick={clearNotifications}
                                        className="text-xs text-sky-600 hover:underline font-normal"
                                    >
                                        Mark all read
                                    </button>
                                )}
                            </div>
                            <div className="max-h-64 overflow-y-auto py-1">
                                {unreadCount > 0 && (
                                    <div 
                                        onClick={() => navigate("/chat")}
                                        className="p-3 rounded-xl hover:bg-slate-50 transition cursor-pointer mb-1 border border-transparent bg-emerald-50/50 border-emerald-100"
                                    >
                                        <p className="text-xs font-semibold text-emerald-800">Unread Chat Messages</p>
                                        <p className="text-[11px] text-emerald-600 mt-1">You have {unreadCount} new unseen chat messages.</p>
                                    </div>
                                )}
                                {notifications.length === 0 && unreadCount === 0 ? (
                                    <div className="py-6 text-center text-xs text-slate-400">No new notifications</div>
                                ) : (
                                    notifications.map((notif: any) => (
                                        <div 
                                            key={notif.id} 
                                            onClick={() => handleNotificationClick(notif)}
                                            className={`p-3 rounded-xl hover:bg-slate-50 transition cursor-pointer mb-1 border border-transparent ${!notif.read ? 'bg-sky-50/50 border-sky-100' : ''}`}
                                        >
                                            <p className="text-xs font-semibold text-slate-900">{notif.title}</p>
                                            <p className="text-[11px] text-slate-500 mt-1 leading-relaxed">{notif.message}</p>
                                            <span className="text-[9px] text-slate-400 mt-2 block">
                                                {new Date(notif.timestamp).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                                            </span>
                                        </div>
                                    ))
                                )}
                            </div>
                        </DropdownMenuContent>
                    </DropdownMenu>
                </div>

                <div className="hidden items-center gap-2 md:flex">
                    <Button variant="ghost" size="sm" onClick={() => navigate("/")}>Dashboard</Button>
                    <Button variant="ghost" size="sm" onClick={() => navigate("/dashboard")}>Browse</Button>
                    <Button variant="ghost" size="sm" onClick={() => navigate("/post-item")}>Report Item</Button>
                    <Button variant="ghost" size="sm" onClick={() => navigate("/chat")} className="gap-2 relative">
                        <MessageCircle className="h-4 w-4" />
                        Chat
                        {unreadCount > 0 && (
                            <span className="absolute -right-2 -top-1 inline-flex h-5 min-w-5 items-center justify-center rounded-full bg-red-600 px-1.5 text-[10px] font-semibold text-white">
                                {unreadCount}
                            </span>
                        )}
                    </Button>
                </div>

                <div className="flex items-center gap-2">
                    {!user ? (
                        <>
                            <Button variant="ghost" size="sm" onClick={() => navigate("/login")}>Log in</Button>
                            <Button size="sm" onClick={() => navigate("/register")}>Sign up</Button>
                        </>
                    ) : (
                        <DropdownMenu>
                            <DropdownMenuTrigger asChild>
                                <Button variant="ghost" size="sm" className="gap-2">
                                    <div className="flex h-8 w-8 items-center justify-center rounded-full bg-slate-100 text-slate-700">
                                        {user.name?.charAt(0).toUpperCase() || "U"}
                                    </div>
                                    <span className="hidden sm:inline">{user.name}</span>
                                    <ChevronDown className="h-4 w-4 text-slate-500" />
                                </Button>
                            </DropdownMenuTrigger>
                            <DropdownMenuContent className="w-56">
                                <div className="px-4 py-3">
                                    <p className="text-sm font-semibold text-slate-900">{user.name}</p>
                                    <p className="text-xs text-slate-500">{user.email}</p>
                                </div>
                                <DropdownMenuSeparator />
                                <DropdownMenuItem onClick={() => navigate("/dashboard")}>
                                    <LayoutDashboard className="h-4 w-4" />
                                    Browse Items
                                </DropdownMenuItem>
                                <DropdownMenuItem onClick={() => navigate("/profile")}>
                                    <UserRound className="h-4 w-4" />
                                    My Profile
                                </DropdownMenuItem>
                                {!isAdmin && (
                                    <DropdownMenuItem onClick={() => navigate("/post-item")}> 
                                        <PlusCircle className="h-4 w-4" />
                                        Post Item
                                    </DropdownMenuItem>
                                )}
                                {isAdmin && (
                                    <DropdownMenuItem onClick={() => navigate("/admin")}> 
                                        <ShieldCheck className="h-4 w-4" />
                                        Admin Panel
                                    </DropdownMenuItem>
                                )}
                                <DropdownMenuSeparator />
                                <DropdownMenuItem onClick={handleLogout} className="text-destructive">
                                    <LogOut className="h-4 w-4" />
                                    Sign Out
                                </DropdownMenuItem>
                            </DropdownMenuContent>
                        </DropdownMenu>
                    )}
                </div>
            </div>
        </nav>
    );
}
