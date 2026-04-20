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
import { GraduationCap, ChevronDown, LogOut, LayoutDashboard, PlusCircle, MessageCircle, ShieldCheck } from "lucide-react";

export default function Navbar() {
    const { user, logout, isAdmin } = useAuth();
    const [, navigate] = useLocation();
    const [unreadCount, setUnreadCount] = useState(0);

    const handleLogout = () => {
        logout();
        navigate("/login");
    };

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
    }, [user]);

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
                                        {user.name.charAt(0).toUpperCase()}
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
