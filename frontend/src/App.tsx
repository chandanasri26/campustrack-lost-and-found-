import { Router, Route } from "wouter";
import { AuthProvider } from "@/contexts/AuthContext";
import Navbar from "@/components/Navbar";
import ProtectedRoute from "@/components/ProtectedRoute";
import Home from "@/pages/Home";
import Dashboard from "@/pages/Dashboard";
import Login from "@/pages/Login";
import Register from "@/pages/Register";
import PostItem from "@/pages/PostItem";
import MyPosts from "@/pages/Myposts";
import AdminDashboard from "@/pages/AdminDashboard";
import ItemDetails from "@/pages/ItemDetails";
import Chat from "@/pages/Chat";

export default function App() {
    return (
        <AuthProvider>
            <Router>
                <Navbar />

                <Route path="/" component={Home} />
                <Route path="/login" component={Login} />
                <Route path="/register" component={Register} />
                <Route path="/dashboard" component={Dashboard} />
                <Route path="/items/:id" component={ItemDetails} />
                <Route path="/chat" component={Chat} />
                <Route
                    path="/post-item"
                    component={() => (
                        <ProtectedRoute>
                            <PostItem />
                        </ProtectedRoute>
                    )}
                />
                <Route
                    path="/my-posts"
                    component={() => (
                        <ProtectedRoute>
                            <MyPosts />
                        </ProtectedRoute>
                    )}
                />
                <Route
                    path="/admin"
                    component={() => (
                        <ProtectedRoute adminOnly>
                            <AdminDashboard />
                        </ProtectedRoute>
                    )}
                />
            </Router>
        </AuthProvider>
    );
}
