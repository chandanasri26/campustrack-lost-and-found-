import { useEffect, useState } from "react";
import { useLocation } from "wouter";
import api from "@/lib/api";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent } from "@/components/ui/card";
import { Search, Package, AlertTriangle, CheckCircle2, ShieldCheck } from "lucide-react";

interface Stats {
    total: number;
    lost: number;
    found: number;
    resolved: number;
}

export default function Home() {
    const [stats, setStats] = useState<Stats | null>(null);
    const [search, setSearch] = useState("");
    const [, navigate] = useLocation();

    useEffect(() => {
        async function loadStats() {
            try {
                const { data } = await api.get("/items/stats");
                setStats(data);
            } catch (error) {
                console.error(error);
            }
        }
        loadStats();
    }, []);

    const handleSearch = () => {
        const trimmed = search.trim();
        navigate(`/dashboard${trimmed ? `?search=${encodeURIComponent(trimmed)}` : ""}`);
    };

    return (
        <main className="min-h-screen bg-slate-50 text-slate-900">
            <section className="relative overflow-hidden bg-gradient-to-b from-blue-500 via-sky-500 to-slate-100 pb-24">
                <div className="absolute inset-x-0 top-0 h-72 bg-[radial-gradient(circle_at_top,_rgba(255,255,255,0.3),_transparent_55%)]" />
                <div className="relative mx-auto max-w-7xl px-4 pt-20 sm:px-6 lg:px-8">
                    <div className="grid gap-16 lg:grid-cols-[1.1fr_0.9fr] lg:items-center">
                        <div className="max-w-2xl">
                            <p className="mb-3 inline-flex rounded-full bg-white/80 px-4 py-1 text-sm font-semibold text-sky-700 shadow-sm shadow-slate-900/5">
                                Campus Community Hub
                            </p>
                            <h1 className="text-5xl font-semibold tracking-tight text-white sm:text-6xl">
                                Lost something? <span className="text-slate-100">Let's find it together.</span>
                            </h1>
                            <p className="mt-6 max-w-xl text-lg text-slate-100/90">
                                The fastest way to report and recover lost items on campus. Search by keyword, browse categories, and post your lost or found report in seconds.
                            </p>

                            <div className="mt-10 flex flex-col gap-3 sm:flex-row sm:items-center">
                                <div className="flex-1 min-w-0">
                                    <label className="sr-only" htmlFor="hero-search">
                                        Search items
                                    </label>
                                    <div className="relative">
                                        <Search className="pointer-events-none absolute left-4 top-1/2 h-5 w-5 -translate-y-1/2 text-slate-400" />
                                        <Input
                                            id="hero-search"
                                            placeholder="Search for keys, wallet, phone..."
                                            value={search}
                                            onChange={(e) => setSearch(e.target.value)}
                                            className="pl-11"
                                        />
                                    </div>
                                </div>
                                <Button onClick={handleSearch} className="whitespace-nowrap">
                                    Search
                                </Button>
                            </div>

                            <div className="mt-6 flex flex-wrap gap-3">
                                <Button variant="outline" onClick={() => navigate("/post-item")}>I lost an item</Button>
                                <Button variant="outline" onClick={() => navigate("/post-item")}>I found an item</Button>
                            </div>
                        </div>

                        <div className="grid gap-4 sm:grid-cols-2">
                            <Card className="rounded-[2rem] border border-white/40 bg-white/90 shadow-2xl">
                                <CardContent className="space-y-3 p-8">
                                    <div className="inline-flex h-12 w-12 items-center justify-center rounded-3xl bg-blue-100 text-blue-700">
                                        <Package className="h-6 w-6" />
                                    </div>
                                    <div>
                                        <p className="text-sm uppercase tracking-[0.22em] text-slate-500">Total Reports</p>
                                        <p className="mt-3 text-4xl font-semibold text-slate-900">{stats?.total ?? "--"}</p>
                                    </div>
                                </CardContent>
                            </Card>
                            <Card className="rounded-[2rem] border border-white/40 bg-white/90 shadow-2xl">
                                <CardContent className="space-y-3 p-8">
                                    <div className="inline-flex h-12 w-12 items-center justify-center rounded-3xl bg-red-100 text-red-700">
                                        <AlertTriangle className="h-6 w-6" />
                                    </div>
                                    <div>
                                        <p className="text-sm uppercase tracking-[0.22em] text-slate-500">Lost Items</p>
                                        <p className="mt-3 text-4xl font-semibold text-slate-900">{stats?.lost ?? "--"}</p>
                                    </div>
                                </CardContent>
                            </Card>
                            <Card className="rounded-[2rem] border border-white/40 bg-white/90 shadow-2xl">
                                <CardContent className="space-y-3 p-8">
                                    <div className="inline-flex h-12 w-12 items-center justify-center rounded-3xl bg-emerald-100 text-emerald-700">
                                        <CheckCircle2 className="h-6 w-6" />
                                    </div>
                                    <div>
                                        <p className="text-sm uppercase tracking-[0.22em] text-slate-500">Found Items</p>
                                        <p className="mt-3 text-4xl font-semibold text-slate-900">{stats?.found ?? "--"}</p>
                                    </div>
                                </CardContent>
                            </Card>
                            <Card className="rounded-[2rem] border border-white/40 bg-white/90 shadow-2xl">
                                <CardContent className="space-y-3 p-8">
                                    <div className="inline-flex h-12 w-12 items-center justify-center rounded-3xl bg-slate-100 text-slate-700">
                                        <ShieldCheck className="h-6 w-6" />
                                    </div>
                                    <div>
                                        <p className="text-sm uppercase tracking-[0.22em] text-slate-500">Resolved</p>
                                        <p className="mt-3 text-4xl font-semibold text-slate-900">{stats?.resolved ?? "--"}</p>
                                    </div>
                                </CardContent>
                            </Card>
                        </div>
                    </div>
                </div>
            </section>

            <section className="mx-auto max-w-7xl px-4 py-16 sm:px-6 lg:px-8">
                <div className="rounded-[2rem] border border-slate-200 bg-white p-8 shadow-lg">
                    <div className="grid gap-8 lg:grid-cols-3 lg:items-center">
                        <div>
                            <p className="text-sm font-semibold uppercase tracking-[0.24em] text-slate-500">Recent Activity</p>
                            <h2 className="mt-4 text-3xl font-semibold text-slate-900">Latest campus reports in one place</h2>
                            <p className="mt-4 text-slate-600">Browse the newest lost and found posts across campus and connect with the owners once you sign in.</p>
                        </div>
                        <div className="lg:col-span-2 grid gap-4 sm:grid-cols-2">
                            <Card className="border-slate-200">
                                <CardContent className="space-y-3 p-5">
                                    <p className="text-sm uppercase tracking-[0.22em] text-slate-500">Wallet Found</p>
                                    <p className="text-lg font-semibold text-slate-900">Black leather wallet in library</p>
                                    <p className="text-sm text-slate-500">Reported 1 hour ago • Library entrance</p>
                                </CardContent>
                            </Card>
                            <Card className="border-slate-200">
                                <CardContent className="space-y-3 p-5">
                                    <p className="text-sm uppercase tracking-[0.22em] text-slate-500">Phone Lost</p>
                                    <p className="text-lg font-semibold text-slate-900">iPhone 14 dropped near cafeteria</p>
                                    <p className="text-sm text-slate-500">Reported 2 hours ago • Student center</p>
                                </CardContent>
                            </Card>
                        </div>
                    </div>
                </div>
            </section>
        </main>
    );
}
