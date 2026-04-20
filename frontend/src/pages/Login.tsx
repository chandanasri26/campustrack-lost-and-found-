import { useState } from "react";
import { useLocation } from "wouter";
import { useAuth } from "@/contexts/AuthContext";
import api from "@/lib/api";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { GraduationCap, Loader2, Eye, EyeOff } from "lucide-react";

export default function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [showPass, setShowPass] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const { login } = useAuth();
    const [, navigate] = useLocation();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");
        setLoading(true);
        try {
            const { data } = await api.post("/auth/login", { email, password });
            login(data.token, data.user);
            navigate(data.user.role === "admin" ? "/admin" : "/dashboard");
        } catch (err: any) {
            setError(err.response?.data?.error || "Login failed. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-primary/5 via-background to-primary/10 px-4 py-12">
            <div className="w-full max-w-md space-y-6">
                <div className="text-center space-y-3">
                    <div className="inline-flex h-16 w-16 items-center justify-center rounded-2xl bg-primary shadow-lg">
                        <GraduationCap className="h-9 w-9 text-primary-foreground" />
                    </div>
                    <div>
                        <h1 className="text-3xl font-bold text-foreground">CampusFind</h1>
                        <p className="text-muted-foreground mt-1">Help reunite lost items with their owners</p>
                    </div>
                </div>

                <Card className="shadow-xl border-border/50">
                    <CardHeader className="pb-4">
                        <CardTitle className="text-xl">Welcome back</CardTitle>
                        <CardDescription>Sign in with your campus email</CardDescription>
                    </CardHeader>
                    <form onSubmit={handleSubmit}>
                        <CardContent className="space-y-4">
                            {error && (
                                <Alert variant="destructive">
                                    <AlertDescription>{error}</AlertDescription>
                                </Alert>
                            )}
                            <div className="space-y-2">
                                <Label htmlFor="email">Email</Label>
                                <Input
                                    id="email"
                                    type="email"
                                    placeholder="student@university.edu"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    required
                                    autoComplete="email"
                                />
                            </div>
                            <div className="space-y-2">
                                <Label htmlFor="password">Password</Label>
                                <div className="relative">
                                    <Input
                                        id="password"
                                        type={showPass ? "text" : "password"}
                                        placeholder="Your password"
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                        required
                                        autoComplete="current-password"
                                        className="pr-10"
                                    />
                                    <button
                                        type="button"
                                        onClick={() => setShowPass(!showPass)}
                                        className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground transition-colors"
                                    >
                                        {showPass ? <EyeOff className="h-4 w-4" /> : <Eye className="h-4 w-4" />}
                                    </button>
                                </div>
                            </div>
                        </CardContent>
                        <CardFooter className="flex flex-col gap-3 pt-2">
                            <Button type="submit" className="w-full" disabled={loading}>
                                {loading && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
                                Sign In
                            </Button>
                            <p className="text-sm text-muted-foreground text-center">
                                Don't have an account?{" "}
                                <button
                                    type="button"
                                    onClick={() => navigate("/register")}
                                    className="text-primary hover:underline font-medium"
                                >
                                    Register here
                                </button>
                            </p>
                        </CardFooter>
                    </form>
                </Card>

                <p className="text-center text-xs text-muted-foreground">
                    Demo admin: <span className="font-mono">admin@campus.edu</span> / <span className="font-mono">admin123</span>
                </p>
            </div>
        </div>
    );
}
