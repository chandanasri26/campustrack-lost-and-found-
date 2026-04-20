import React from "react";

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
    className?: string;
}

export function Input({ className = "", ...props }: InputProps) {
    return (
        <input
            className={`w-full rounded-md border border-slate-300 bg-white px-3 py-2 text-sm text-slate-900 shadow-sm outline-none transition focus:border-primary focus:ring-2 focus:ring-primary/10 ${className}`}
            {...props}
        />
    );
}
