import { useState, useRef } from "react";
import { useLocation } from "wouter";
import api from "@/lib/api";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Card, CardContent } from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Loader2, ImagePlus, X } from "lucide-react";
import { useToast } from "@/hooks/use-toast";

const CATEGORIES = ["Electronics", "Clothing", "Books", "Accessories", "Keys", "Wallet/Purse", "Bag", "Sports", "Other"];

export default function PostItem() {
    const [form, setForm] = useState({
        title: "",
        description: "",
        category: "",
        location: "",
        date: "",
        tags: "",
        imageUrl: "",
        type: "lost",
    });
    const [imagePreview, setImagePreview] = useState<string | null>(null);
    const [uploading, setUploading] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const fileRef = useRef<HTMLInputElement>(null);
    const [, navigate] = useLocation();
    const { toast } = useToast();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleImage = async (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (!file) return;
        if (file.size > 5 * 1024 * 1024) {
            setError("Image must be under 5MB.");
            return;
        }
        const reader = new FileReader();
        reader.onload = async (ev) => {
            const base64 = ev.target?.result as string;
            setImagePreview(base64);
            setUploading(true);
            try {
                const { data } = await api.post("/items/upload-image", {
                    imageData: base64,
                    fileName: file.name,
                });
                setForm((f) => ({ ...f, imageUrl: data.imageUrl }));
            } catch {
                setError("Image upload failed. You can still post without an image.");
                setImagePreview(null);
            } finally {
                setUploading(false);
            }
        };
        reader.readAsDataURL(file);
    };

    const removeImage = () => {
        setImagePreview(null);
        setForm((f) => ({ ...f, imageUrl: "" }));
        if (fileRef.current) fileRef.current.value = "";
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");
        if (!form.category) {
            setError("Please select a category.");
            return;
        }
        setLoading(true);
        try {
            await api.post("/items", form);
            toast({ title: "Item posted!", description: "Your item has been added to the board." });
            navigate("/my-posts");
        } catch (err: any) {
            setError(err.response?.data?.error || "Failed to post item.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-slate-50">
            <main className="mx-auto w-full max-w-4xl px-4 py-10 sm:px-6 lg:px-8">
                <div className="mb-8 flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
                    <div>
                        <h1 className="text-3xl font-semibold text-slate-900">Report Item</h1>
                        <p className="mt-3 max-w-2xl text-sm text-slate-600">Fill in the item details and submit it for others to see.</p>
                    </div>
                    <Button variant="outline" size="sm" onClick={() => navigate("/dashboard")}>Back to Dashboard</Button>
                </div>

                <Card className="overflow-hidden border border-slate-200 shadow-sm">
                    <form onSubmit={handleSubmit}>
                        <CardContent className="space-y-6 bg-white px-6 py-8">
                            {error && (
                                <Alert variant="destructive">
                                    <AlertDescription>{error}</AlertDescription>
                                </Alert>
                            )}

                            <div className="grid gap-6 lg:grid-cols-[1fr_220px]">
                                <div className="space-y-4">
                                    <div className="space-y-2">
                                        <Label htmlFor="title">Item name</Label>
                                        <Input id="title" name="title" placeholder="e.g. Blue water bottle" value={form.title} onChange={handleChange} required />
                                    </div>

                                    <div className="space-y-2">
                                        <Label htmlFor="category">Category</Label>
                                        <Select value={form.category} onValueChange={(v) => setForm({ ...form, category: v })}>
                                            <SelectTrigger>
                                                <SelectValue placeholder="Select category" />
                                            </SelectTrigger>
                                            <SelectContent>
                                                {CATEGORIES.map((c) => <SelectItem key={c} value={c}>{c}</SelectItem>)}
                                            </SelectContent>
                                        </Select>
                                    </div>

                                    <div className="space-y-2">
                                        <Label htmlFor="description">Description</Label>
                                        <Textarea id="description" name="description" placeholder="Describe the item in detail..." value={form.description} onChange={handleChange} rows={5} />
                                    </div>
                                </div>

                                <div className="space-y-4">
                                    <div className="grid gap-4 sm:grid-cols-2">
                                        <div className="space-y-2">
                                            <Label htmlFor="location">Location</Label>
                                            <Input id="location" name="location" placeholder="e.g. Library" value={form.location} onChange={handleChange} />
                                        </div>
                                        <div className="space-y-2">
                                            <Label htmlFor="date">Date</Label>
                                            <Input id="date" name="date" type="date" value={form.date} onChange={handleChange} />
                                        </div>
                                    </div>

                                    <div className="space-y-2">
                                        <Label htmlFor="tags">Tags <span className="text-xs text-slate-400">comma separated</span></Label>
                                        <Input id="tags" name="tags" placeholder="e.g. blue, bottle, campus" value={form.tags} onChange={handleChange} />
                                    </div>

                                    <div className="space-y-2">
                                        <Label>Image</Label>
                                        {imagePreview ? (
                                            <div className="relative inline-block">
                                                <img src={imagePreview} alt="Preview" className="h-40 w-full max-w-xs rounded-2xl border border-slate-200 object-cover" />
                                                {uploading && (
                                                    <div className="absolute inset-0 flex items-center justify-center rounded-2xl bg-white/80">
                                                        <Loader2 className="h-5 w-5 animate-spin" />
                                                    </div>
                                                )}
                                                <button
                                                    type="button"
                                                    onClick={removeImage}
                                                    className="absolute -top-2 -right-2 inline-flex h-8 w-8 items-center justify-center rounded-full bg-slate-900 text-white shadow-lg"
                                                >
                                                    <X className="h-4 w-4" />
                                                </button>
                                            </div>
                                        ) : (
                                            <button
                                                type="button"
                                                onClick={() => fileRef.current?.click()}
                                                className="flex h-32 w-full max-w-xs flex-col items-center justify-center gap-2 rounded-3xl border border-dashed border-slate-300 bg-slate-50 text-slate-500 transition hover:border-slate-400 hover:bg-white"
                                            >
                                                <ImagePlus className="h-6 w-6" />
                                                <span className="text-sm">Choose file</span>
                                                <span className="text-xs">PNG, JPG up to 5MB</span>
                                            </button>
                                        )}
                                        <input ref={fileRef} type="file" accept="image/*" className="hidden" onChange={handleImage} />
                                    </div>
                                </div>
                            </div>
                        </CardContent>

                        <div className="flex flex-col gap-3 border-t border-slate-200 bg-slate-50 px-6 py-4 sm:flex-row sm:justify-end">
                            <Button type="button" variant="outline" onClick={() => navigate("/dashboard")} className="w-full sm:w-auto">Cancel</Button>
                            <Button type="submit" disabled={loading || uploading} className="w-full sm:w-auto bg-slate-900 text-white hover:bg-slate-800 disabled:opacity-60 disabled:cursor-not-allowed">
                                {loading && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                                Report Item
                            </Button>
                        </div>
                    </form>
                </Card>
            </main>
        </div>
    );
}
