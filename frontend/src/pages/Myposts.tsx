import { useState, useEffect } from "react";
import { useLocation } from "wouter";
import api from "@/lib/api";
import ItemCard, { Item } from "@/components/ItemCard";
import { Button } from "@/components/ui/button";
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Skeleton } from "@/components/ui/skeleton";
import { Card, CardContent } from "@/components/ui/card";
import { PlusCircle, BookMarked, Loader2 } from "lucide-react";
import { useToast } from "@/hooks/use-toast";
import { useAuth } from "@/contexts/AuthContext";

const CATEGORIES = ["Electronics", "Clothing", "Books", "Accessories", "Keys", "Wallet/Purse", "Bag", "Sports", "Other"];

export default function MyPosts() {
    const [items, setItems] = useState<Item[]>([]);
    const [loading, setLoading] = useState(true);
    const [editItem, setEditItem] = useState<Item | null>(null);
    const [editForm, setEditForm] = useState({ title: "", description: "", category: "", location: "", status: "" });
    const [saving, setSaving] = useState(false);
    const { user } = useAuth();
    const [, navigate] = useLocation();
    const { toast } = useToast();

    const fetchMyItems = async () => {
        try {
            const { data } = await api.get("/items/my");
            setItems(data);
        } catch {
            toast({ title: "Error", description: "Failed to load your items.", variant: "destructive" });
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => { fetchMyItems(); }, []);

    const openEdit = (item: Item) => {
        setEditItem(item);
        setEditForm({ title: item.title, description: item.description, category: item.category, location: item.location, status: item.status });
    };

    const saveEdit = async () => {
        if (!editItem) return;
        setSaving(true);
        try {
            const { data } = await api.patch(`/items/${editItem.id}`, editForm);
            setItems((prev) => prev.map((i) => (i.id === editItem.id ? data : i)));
            setEditItem(null);
            toast({ title: "Updated!", description: "Item updated successfully." });
        } catch (err: any) {
            toast({ title: "Error", description: err.response?.data?.error || "Update failed.", variant: "destructive" });
        } finally {
            setSaving(false);
        }
    };

    const handleDelete = async (id: string) => {
        if (!confirm("Delete this item? This cannot be undone.")) return;
        try {
            await api.delete(`/items/${id}`);
            setItems((prev) => prev.filter((i) => i.id !== id));
            toast({ title: "Deleted", description: "Item removed from the board." });
        } catch {
            toast({ title: "Error", description: "Delete failed.", variant: "destructive" });
        }
    };

    const handleResolve = async (id: string) => {
        try {
            const { data } = await api.patch(`/items/${id}/resolve`);
            setItems((prev) => prev.map((i) => (i.id === id ? data : i)));
            toast({ title: "Resolved!", description: "Item marked as resolved." });
        } catch {
            toast({ title: "Error", description: "Failed to resolve item.", variant: "destructive" });
        }
    };

    return (
        <div className="min-h-screen bg-background">
            <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
                <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 mb-8">
                    <div className="flex items-center gap-3">
                        <BookMarked className="h-6 w-6 text-primary" />
                        <div>
                            <h1 className="text-2xl font-bold text-foreground">My Posts</h1>
                            <p className="text-muted-foreground text-sm">Items you've reported</p>
                        </div>
                    </div>
                    <Button onClick={() => navigate("/post-item")} className="gap-2 shrink-0">
                        <PlusCircle className="h-4 w-4" />
                        Post New Item
                    </Button>
                </div>

                {loading ? (
                    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
                        {Array.from({ length: 4 }).map((_, i) => (
                            <Card key={i}>
                                <Skeleton className="h-44 rounded-none rounded-t-lg" />
                                <CardContent className="p-4 space-y-2">
                                    <Skeleton className="h-4 w-3/4" />
                                    <Skeleton className="h-3 w-full" />
                                </CardContent>
                            </Card>
                        ))}
                    </div>
                ) : items.length === 0 ? (
                    <div className="flex flex-col items-center justify-center py-20 text-center">
                        <BookMarked className="h-14 w-14 text-muted-foreground/30 mb-4" />
                        <h3 className="text-lg font-medium">No posts yet</h3>
                        <p className="text-muted-foreground text-sm mt-1 mb-4">You haven't posted any items yet.</p>
                        <Button onClick={() => navigate("/post-item")} size="sm" className="gap-2">
                            <PlusCircle className="h-4 w-4" />
                            Post Your First Item
                        </Button>
                    </div>
                ) : (
                    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
                        {items.map((item) => (
                            <ItemCard
                                key={item.id}
                                item={item}
                                onEdit={openEdit}
                                onDelete={handleDelete}
                                onResolve={handleResolve}
                                isOwner={true}
                            />
                        ))}
                    </div>
                )}
            </main>

            {/* Edit Dialog */}
            <Dialog open={!!editItem} onOpenChange={() => setEditItem(null)}>
                <DialogContent className="sm:max-w-lg">
                    <DialogHeader>
                        <DialogTitle>Edit Item</DialogTitle>
                        <DialogDescription>Update the details of your posting.</DialogDescription>
                    </DialogHeader>
                    <div className="space-y-4 py-2">
                        <div className="space-y-2">
                            <Label>Title</Label>
                            <Input value={editForm.title} onChange={(e) => setEditForm({ ...editForm, title: e.target.value })} />
                        </div>
                        <div className="space-y-2">
                            <Label>Description</Label>
                            <Textarea value={editForm.description} onChange={(e) => setEditForm({ ...editForm, description: e.target.value })} rows={3} />
                        </div>
                        <div className="grid grid-cols-2 gap-4">
                            <div className="space-y-2">
                                <Label>Category</Label>
                                <Select value={editForm.category} onValueChange={(v) => setEditForm({ ...editForm, category: v })}>
                                    <SelectTrigger><SelectValue /></SelectTrigger>
                                    <SelectContent>
                                        {CATEGORIES.map((c) => <SelectItem key={c} value={c}>{c}</SelectItem>)}
                                    </SelectContent>
                                </Select>
                            </div>
                            <div className="space-y-2">
                                <Label>Status</Label>
                                <Select value={editForm.status} onValueChange={(v) => setEditForm({ ...editForm, status: v })}>
                                    <SelectTrigger><SelectValue /></SelectTrigger>
                                    <SelectContent>
                                        <SelectItem value="open">Open</SelectItem>
                                        <SelectItem value="resolved">Resolved</SelectItem>
                                    </SelectContent>
                                </Select>
                            </div>
                        </div>
                        <div className="space-y-2">
                            <Label>Location</Label>
                            <Input value={editForm.location} onChange={(e) => setEditForm({ ...editForm, location: e.target.value })} />
                        </div>
                    </div>
                    <DialogFooter>
                        <Button variant="outline" onClick={() => setEditItem(null)}>Cancel</Button>
                        <Button onClick={saveEdit} disabled={saving}>
                            {saving && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                            Save Changes
                        </Button>
                    </DialogFooter>
                </DialogContent>
            </Dialog>
        </div>
    );
}
