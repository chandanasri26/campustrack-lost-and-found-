import { useState, useEffect } from "react";
import { useLocation, useRoute } from "wouter";
import { Card, CardContent, CardHeader, CardTitle, CardFooter } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { ArrowLeft, MapPin, Clock, MessageSquare } from "lucide-react";
import { itemApi } from "@/lib/api";
import { useAuth } from "@/contexts/AuthContext";
import { Item } from "@/types";

export default function ItemDetails() {
    const [match, params] = useRoute("/items/:id");
    const [, navigate] = useLocation();
    const { user } = useAuth();
    const [item, setItem] = useState<Item | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const itemId = params?.id;
        if (!itemId) return;

        const load = async () => {
            try {
                setLoading(true);
                const response = await itemApi.getItem(itemId);
                setItem(response.data);
            } catch (error) {
                console.error("Failed to load item details:", error);
            } finally {
                setLoading(false);
            }
        };

        load();
    }, [params]);

    const handleMessage = () => {
        if (!item || !user) return;
        if (item.userId === user.id) return;

        navigate(
            `/chat?itemId=${item.id}&receiverId=${item.userId}&receiverName=${encodeURIComponent(
                item.userName,
            )}&itemTitle=${encodeURIComponent(item.title)}&itemCategory=${encodeURIComponent(
                item.category,
            )}&itemType=${encodeURIComponent(item.type)}`,
        );
    };

    const formatDate = (value: string) => new Date(value).toLocaleString();

    return (
        <div className="min-h-screen bg-background py-8">
            <main className="mx-auto max-w-6xl px-4 sm:px-6 lg:px-8">
                <div className="mb-6">
                    <Button variant="ghost" onClick={() => navigate("/dashboard")} className="gap-2">
                        <ArrowLeft className="h-4 w-4" />
                        Back
                    </Button>
                </div>

                <div className="grid gap-6 lg:grid-cols-[1.8fr_1fr]">
                    <Card className="overflow-hidden">
                        <CardHeader className="space-y-3 px-6 pt-6 pb-0">
                            <div className="flex items-center gap-3">
                                <Badge variant={item?.type === "lost" ? "destructive" : "default"}>
                                    {item?.type === "lost" ? "Lost Item" : "Found Item"}
                                </Badge>
                            </div>
                            <div>
                                <CardTitle className="text-3xl tracking-tight">{item?.title || "Item details"}</CardTitle>
                                <p className="text-sm text-slate-600 mt-1">{item?.description}</p>
                            </div>
                        </CardHeader>
                        <div className="h-[420px] overflow-hidden bg-slate-100">
                            {item?.imageUrl ? (
                                <img src={item.imageUrl} alt={item?.title} className="h-full w-full object-cover" />
                            ) : (
                                <div className="flex h-full items-center justify-center text-slate-500">No image available</div>
                            )}
                        </div>
                    </Card>

                    <div className="space-y-6">
                        <Card>
                            <CardHeader>
                                <CardTitle className="text-lg">Details</CardTitle>
                            </CardHeader>
                            <CardContent className="space-y-4">
                                <div className="space-y-2">
                                    <p className="text-xs uppercase tracking-[0.2em] text-slate-500">Location</p>
                                    <p className="text-sm text-slate-900">{item?.location || "Unknown"}</p>
                                </div>
                                <div className="space-y-2">
                                    <p className="text-xs uppercase tracking-[0.2em] text-slate-500">Category</p>
                                    <p className="text-sm text-slate-900">{item?.category || "General"}</p>
                                </div>
                                <div className="space-y-2">
                                    <p className="text-xs uppercase tracking-[0.2em] text-slate-500">Reported</p>
                                    <p className="text-sm text-slate-900">{item ? formatDate(item.createdAt) : "-"}</p>
                                </div>
                                <div className="space-y-2">
                                    <p className="text-xs uppercase tracking-[0.2em] text-slate-500">Status</p>
                                    <p className="text-sm text-slate-900">{item?.status === "resolved" ? "Resolved" : "Open"}</p>
                                </div>
                            </CardContent>
                        </Card>

                        <Card className="overflow-hidden">
                            <CardHeader>
                                <CardTitle className="text-lg">Contact</CardTitle>
                            </CardHeader>
                            <CardContent className="space-y-4">
                                <div className="flex items-center gap-4">
                                    <div className="flex h-11 w-11 items-center justify-center rounded-full bg-slate-100 text-slate-700">
                                        {item?.userName?.charAt(0).toUpperCase()}
                                    </div>
                                    <div>
                                        <p className="text-sm font-semibold text-slate-900">{item?.userName}</p>
                                        <p className="text-xs text-slate-500">Reporter</p>
                                    </div>
                                </div>
                                <Button
                                    onClick={handleMessage}
                                    disabled={!item || !user || item.userId === user.id}
                                    size="lg"
                                    className="w-full bg-sky-600 text-white hover:bg-sky-700 text-base font-semibold py-6"
                                >
                                    <MessageSquare className="mr-2 h-5 w-5" />
                                    Message Owner
                                </Button>
                            </CardContent>
                        </Card>
                    </div>
                </div>
            </main>
        </div>
    );
}
