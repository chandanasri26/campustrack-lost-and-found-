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

export default function Register() {
    const [form, setForm] = useState({ name: "", email: "", password: "", studentId: "", role: "student" });
    const [showPass, setShowPass] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const { login } = useAuth();
    const [, navigate] = useLocation();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");
        if (form.password.length < 6) {
            setError("Password must be at least 6 characters.");
            return;
        }
        setLoading(true);
        try {
            const { data } = await api.post("/auth/register", form);
            login(data.token, data.user);
            navigate("/dashboard");
        } catch (err: any) {
            setError(
                err.response?.data?.error ||
                err.response?.data?.message ||
                err.message ||
                "Registration failed. Please try again."
            );
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
                        <h1 className="text-3xl font-bold text-foreground">Join CampusFind</h1>
                        <p className="text-muted-foreground mt-1">Create your student account</p>
                    </div>
                </div>

                <Card className="shadow-xl border-border/50">
                    <CardHeader className="pb-4">
                        <CardTitle className="text-xl">Create Account</CardTitle>
                        <CardDescription>Fill in your details to get started</CardDescription>
                    </CardHeader>
                    <form onSubmit={handleSubmit}>
                        <CardContent className="space-y-4">
                            {error && (
                                <Alert variant="destructive">
                                    <AlertDescription>{error}</AlertDescription>
                                </Alert>
                            )}
                            <div className="space-y-2">
                                <Label htmlFor="name">Full Name</Label>
                                <Input id="name" name="name" placeholder="John Smith" value={form.name} onChange={handleChange} required />
                            </div>
                            <div className="space-y-2">
                                <Label htmlFor="email">Email</Label>
                                <Input id="email" name="email" type="email" placeholder="john@university.edu" value={form.email} onChange={handleChange} required />
                            </div>
                            <div className="space-y-2">
                                <Label>Role</Label>
                                <div className="grid grid-cols-2 gap-2">
                                    {(["student", "admin"] as const).map((roleOption) => (
                                        <button
                                            key={roleOption}
                                            type="button"
                                            onClick={() => setForm({ ...form, role: roleOption })}
                                            className={`rounded-xl border px-4 py-3 text-sm font-semibold transition ${form.role === roleOption ? "border-sky-600 bg-sky-600 text-white" : "border-slate-300 bg-white text-slate-700 hover:border-slate-400"}`}
                                        >
                                            {roleOption === "student" ? "Student" : "Admin"}
                                        </button>
                                    ))}
                                </div>
                            </div>
                            <div className="space-y-2">
                                <Label htmlFor="studentId">Student ID</Label>
                                <Input id="studentId" name="studentId" placeholder="e.g. STU2024001" value={form.studentId} onChange={handleChange} required />
                            </div>
                            <div className="space-y-2">
                                <Label htmlFor="password">Password</Label>
                                <div className="relative">
                                    <Input
                                        id="password"
                                        name="password"
                                        type={showPass ? "text" : "password"}
                                        placeholder="At least 6 characters"
                                        value={form.password}
                                        onChange={handleChange}
                                        required
                                        className="pr-10"
                                    />
                                    <button
                                        type="button"
                                        onClick={() => setShowPass(!showPass)}
                                        className="absolute right-3 top-1/2 -translate-y-1/2 text-muted-foreground hover:text-foreground"
                                    >
                                        {showPass ? <EyeOff className="h-4 w-4" /> : <Eye className="h-4 w-4" />}
                                    </button>
                                </div>
                            </div>
                            <div className="pt-4">
                                <button
                                    type="submit"
                                    disabled={loading}
                                    className="w-full rounded-xl bg-sky-600 px-4 py-3 text-sm font-semibold text-white transition hover:bg-sky-700 disabled:cursor-not-allowed disabled:opacity-60"
                                >
                                    {loading ? (
                                        <span className="inline-flex items-center justify-center gap-2">
                                            <Loader2 className="h-4 w-4 animate-spin" />
                                            Signing up...
                                        </span>
                                    ) : (
                                        "Sign up"
                                    )}
                                </button>
                            </div>
                        </CardContent>
                        <CardFooter className="flex justify-center pt-2">
                            <p className="text-sm text-muted-foreground text-center">
                                Already have an account?{" "}
                                <button type="button" onClick={() => navigate("/login")} className="text-primary hover:underline font-medium">
                                    Sign in
                                </button>
                            </p>
                        </CardFooter>
                    </form>
                </Card>
            </div>
        </div>
    );
}
