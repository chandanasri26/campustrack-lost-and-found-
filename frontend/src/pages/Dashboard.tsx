import { useState, useEffect, useCallback } from "react";
import { useLocation } from "wouter";
import api from "@/lib/api";
import { useAuth } from "@/contexts/AuthContext";
import ItemCard, { Item } from "@/components/ItemCard";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Badge } from "@/components/ui/badge";
import { Skeleton } from "@/components/ui/skeleton";
import { Card, CardContent } from "@/components/ui/card";
import { Search, PlusCircle, Package, AlertTriangle, CheckCircle2, BarChart3, X } from "lucide-react";
import { useToast } from "@/hooks/use-toast";

interface Stats {
    total: number;
    lost: number;
    found: number;
    open: number;
    resolved: number;
}

const CATEGORIES = ["Electronics", "Clothing", "Books", "Accessories", "Keys", "Wallet/Purse", "Bag", "Sports", "Other"];

export default function Dashboard() {
    const [items, setItems] = useState<Item[]>([]);
    const [stats, setStats] = useState<Stats | null>(null);
    const [loading, setLoading] = useState(true);
    const [search, setSearch] = useState("");
    const [category, setCategory] = useState("");
    const [type, setType] = useState("");
    const [status, setStatus] = useState("");
    const { user } = useAuth();
    const [, navigate] = useLocation();
    const { toast } = useToast();

    const fetchItems = useCallback(async () => {
        try {
            const params: Record<string, string> = {};
            if (search) params.search = search;
            if (category && category !== "all") params.category = category;
            if (type && type !== "all") params.type = type;
            if (status && status !== "all") params.status = status;
            if (user?.role !== "admin") {
                params.approved = "true";
            }
            const { data } = await api.get("/items", { params });
            setItems(data);
        } catch {
            toast({ title: "Error", description: "Failed to load items.", variant: "destructive" });
        } finally {
            setLoading(false);
        }
    }, [search, category, type, status, toast, user]);

    const fetchStats = async () => {
        try {
            const { data } = await api.get("/items/stats");
            setStats(data);
        } catch { }
    };

    useEffect(() => { fetchStats(); }, []);
    useEffect(() => {
        const t = setTimeout(fetchItems, 300);
        return () => clearTimeout(t);
    }, [fetchItems]);

    const clearFilters = () => {
        setSearch(""); setCategory(""); setType(""); setStatus("");
    };
    const hasFilters = search || (category && category !== "all") || (type && type !== "all") || (status && status !== "all");

    return (
        <div className="min-h-screen bg-background">
            <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
                {/* Header */}
                <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-8">
                    <div>
                        <h1 className="text-2xl font-bold text-foreground">Lost & Found Board</h1>
                        <p className="text-muted-foreground text-sm mt-0.5">Browse all reported items across campus</p>
                    </div>
                    {user?.role !== "admin" && (
                        <Button onClick={() => navigate("/post-item")} className="gap-2 shrink-0">
                            <PlusCircle className="h-4 w-4" />
                            Post an Item
                        </Button>
                    )}
                </div>

                {/* Stats */}
                {stats && (
                    <div className="grid grid-cols-2 sm:grid-cols-4 gap-3 mb-8">
                        {[
                            { label: "Total Items", value: stats.total, icon: Package, color: "text-primary" },
                            { label: "Lost", value: stats.lost, icon: AlertTriangle, color: "text-destructive" },
                            { label: "Found", value: stats.found, icon: CheckCircle2, color: "text-green-600" },
                            { label: "Resolved", value: stats.resolved, icon: BarChart3, color: "text-blue-600" },
                        ].map(({ label, value, icon: Icon, color }) => (
                            <Card key={label} className="border-border/50">
                                <CardContent className="p-4 flex items-center gap-3">
                                    <Icon className={`h-7 w-7 ${color} shrink-0`} />
                                    <div>
                                        <p className="text-2xl font-bold text-foreground">{value}</p>
                                        <p className="text-xs text-muted-foreground">{label}</p>
                                    </div>
                                </CardContent>
                            </Card>
                        ))}
                    </div>
                )}

                {/* Filters */}
                <div className="flex flex-col sm:flex-row gap-3 mb-6">
                    <div className="relative flex-1">
                        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                        <Input
                            placeholder="Search items..."
                            value={search}
                            onChange={(e) => setSearch(e.target.value)}
                            className="pl-9"
                        />
                    </div>
                    <Select value={category || "all"} onValueChange={(v) => setCategory(v === "all" ? "" : v)}>
                        <SelectTrigger className="w-full sm:w-40">
                            <SelectValue placeholder="Category" />
                        </SelectTrigger>
                        <SelectContent>
                            <SelectItem value="all">All Categories</SelectItem>
                            {CATEGORIES.map((c) => <SelectItem key={c} value={c}>{c}</SelectItem>)}
                        </SelectContent>
                    </Select>
                    <Select value={type || "all"} onValueChange={(v) => setType(v === "all" ? "" : v)}>
                        <SelectTrigger className="w-full sm:w-32">
                            <SelectValue placeholder="Type" />
                        </SelectTrigger>
                        <SelectContent>
                            <SelectItem value="all">All Types</SelectItem>
                            <SelectItem value="lost">Lost</SelectItem>
                            <SelectItem value="found">Found</SelectItem>
                        </SelectContent>
                    </Select>
                    <Select value={status || "all"} onValueChange={(v) => setStatus(v === "all" ? "" : v)}>
                        <SelectTrigger className="w-full sm:w-32">
                            <SelectValue placeholder="Status" />
                        </SelectTrigger>
                        <SelectContent>
                            <SelectItem value="all">All Status</SelectItem>
                            <SelectItem value="open">Open</SelectItem>
                            <SelectItem value="resolved">Resolved</SelectItem>
                        </SelectContent>
                    </Select>
                    {hasFilters && (
                        <Button variant="ghost" size="icon" onClick={clearFilters} title="Clear filters">
                            <X className="h-4 w-4" />
                        </Button>
                    )}
                </div>

                {/* Results */}
                <div className="flex items-center justify-between mb-4">
                    <p className="text-sm text-muted-foreground">
                        {loading ? "Loading..." : `${items.length} item${items.length !== 1 ? "s" : ""} found`}
                    </p>
                    {hasFilters && (
                        <Badge variant="secondary" className="text-xs">Filtered</Badge>
                    )}
                </div>

                {loading ? (
                    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
                        {Array.from({ length: 8 }).map((_, i) => (
                            <Card key={i}>
                                <Skeleton className="h-44 rounded-none rounded-t-lg" />
                                <CardContent className="p-4 space-y-2">
                                    <Skeleton className="h-4 w-3/4" />
                                    <Skeleton className="h-3 w-full" />
                                    <Skeleton className="h-3 w-2/3" />
                                </CardContent>
                            </Card>
                        ))}
                    </div>
                ) : items.length === 0 ? (
                    <div className="flex flex-col items-center justify-center py-20 text-center">
                        <Package className="h-14 w-14 text-muted-foreground/30 mb-4" />
                        <h3 className="text-lg font-medium text-foreground">No items found</h3>
                        <p className="text-muted-foreground text-sm mt-1 mb-4">
                            {hasFilters ? "Try adjusting your filters." : "Be the first to post a lost or found item!"}
                        </p>
                        {user?.role !== "admin" && (
                            <Button onClick={() => navigate("/post-item")} size="sm" className="gap-2">
                                <PlusCircle className="h-4 w-4" />
                                Post an Item
                            </Button>
                        )}
                    </div>
                ) : (
                    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
                        {items.map((item) => (
                            <ItemCard
                                key={item.id}
                                item={item}
                                isOwner={item.userId === user?.id}
                                onView={() => navigate(`/items/${item.id}`)}
                                onMessage={
                                    item.userId !== user?.id
                                        ? () => navigate(
                                              `/chat?itemId=${item.id}&receiverId=${item.userId}&receiverName=${encodeURIComponent(
                                                  item.userName,
                                              )}&itemTitle=${encodeURIComponent(item.title)}&itemCategory=${encodeURIComponent(
                                                  item.category,
                                              )}&itemType=${encodeURIComponent(item.type)}`,
                                          )
                                        : undefined
                                }
                            />
                        ))}
                    </div>
                )}
            </main>
        </div>
    );
}
