import { useAuth } from "@/contexts/AuthContext";
import { Redirect } from "wouter";

interface ProtectedRouteProps {
    children: React.ReactNode;
    adminOnly?: boolean;
}

export default function ProtectedRoute({ children, adminOnly = false }: ProtectedRouteProps) {
    const { isAuthenticated, isAdmin } = useAuth();

    if (!isAuthenticated) {
        return <Redirect to="/login" />;
    }

    if (adminOnly && !isAdmin) {
        return <Redirect to="/dashboard" />;
    }

    return <>{children}</>;
}
