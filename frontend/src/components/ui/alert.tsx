import React from "react";

interface AlertProps extends React.HTMLAttributes<HTMLDivElement> {
    variant?: string;
    className?: string;
}

export function Alert({ variant, className = "", ...props }: AlertProps) {
    const base = "rounded-lg border p-4 text-sm";
    const variantClasses = variant === "destructive"
        ? "border-red-200 bg-red-50 text-red-900"
        : "border-slate-200 bg-slate-50 text-slate-900";

    return <div className={`${base} ${variantClasses} ${className}`} {...props} />;
}

export function AlertDescription({ className = "", ...props }: React.HTMLAttributes<HTMLParagraphElement>) {
    return <p className={`text-sm ${className}`} {...props} />;
}
