import React from "react";

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
    variant?: string;
    size?: string;
    className?: string;
}

export function Button({ variant, size, className = "", ...props }: ButtonProps) {
    const base = "inline-flex items-center justify-center rounded-md border px-4 py-2 text-sm font-medium transition-colors focus:outline-none focus:ring-2 focus:ring-primary/50";
    const variantClasses = variant === "ghost"
        ? "border-transparent bg-transparent hover:bg-slate-100 text-slate-900"
        : variant === "outline"
        ? "border-slate-300 bg-white text-slate-900 hover:bg-slate-50"
        : "border-transparent bg-primary text-white hover:bg-primary/90";
    const sizeClasses = size === "sm" ? "px-3 py-1.5 text-xs" : "px-4 py-2 text-sm";

    return <button className={`${base} ${variantClasses} ${sizeClasses} ${className}`} {...props} />;
}
