import React from "react";

interface TextareaProps extends React.TextareaHTMLAttributes<HTMLTextAreaElement> {
    className?: string;
}

export function Textarea({ className = "", ...props }: TextareaProps) {
    return (
        <textarea
            className={`w-full rounded-md border border-slate-300 bg-white px-3 py-2 text-sm text-slate-900 shadow-sm outline-none transition focus:border-primary focus:ring-2 focus:ring-primary/10 ${className}`}
            {...props}
        />
    );
}
