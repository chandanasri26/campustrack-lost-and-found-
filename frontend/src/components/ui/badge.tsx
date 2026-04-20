import React from "react";

interface BadgeProps extends React.HTMLAttributes<HTMLSpanElement> {
    variant?: string;
    className?: string;
}

export function Badge({ variant, className = "", children, ...props }: BadgeProps) {
    const base = "inline-flex items-center rounded-full px-2.5 py-0.5 text-xs font-semibold";
    const variantClasses = variant === "secondary"
        ? "bg-slate-100 text-slate-700"
        : variant === "destructive"
        ? "bg-red-100 text-red-700"
        : "bg-primary text-primary-foreground";

    return (
        <span className={`${base} ${variantClasses} ${className}`} {...props}>
            {children}
        </span>
    );
}
