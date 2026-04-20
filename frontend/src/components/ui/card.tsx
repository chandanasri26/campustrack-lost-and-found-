import React from "react";

interface CardProps extends React.HTMLAttributes<HTMLDivElement> {
    className?: string;
}

export function Card({ className = "", ...props }: CardProps) {
    return <div className={`rounded-2xl border border-slate-200 bg-white shadow-sm ${className}`} {...props} />;
}

export function CardHeader({ className = "", ...props }: CardProps) {
    return <div className={`px-4 py-4 ${className}`} {...props} />;
}

export function CardContent({ className = "", ...props }: CardProps) {
    return <div className={`px-4 py-2 ${className}`} {...props} />;
}

export function CardFooter({ className = "", ...props }: CardProps) {
    return <div className={`px-4 py-3 ${className}`} {...props} />;
}

export function CardTitle({ className = "", ...props }: CardProps) {
    return <h2 className={`text-lg font-semibold ${className}`} {...props} />;
}

export function CardDescription({ className = "", ...props }: CardProps) {
    return <p className={`text-sm text-slate-500 ${className}`} {...props} />;
}
