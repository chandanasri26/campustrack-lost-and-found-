import React from "react";

interface DialogProps {
    open: boolean;
    onOpenChange: (open: boolean) => void;
    children: React.ReactNode;
}

export function Dialog({ open, onOpenChange, children }: DialogProps) {
    if (!open) return null;

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/40 p-4" onClick={() => onOpenChange(false)}>
            <div onClick={(e) => e.stopPropagation()}>{children}</div>
        </div>
    );
}

interface DialogContentProps extends React.HTMLAttributes<HTMLDivElement> {
    className?: string;
    children: React.ReactNode;
}

export function DialogContent({ className = "", children, ...props }: DialogContentProps) {
    return (
        <div className={`w-full max-w-2xl rounded-3xl bg-white p-6 shadow-xl ${className}`} {...props} />
    );
}

export function DialogHeader({ className = "", ...props }: React.HTMLAttributes<HTMLDivElement>) {
    return <div className={`mb-4 ${className}`} {...props} />;
}

export function DialogTitle({ className = "", ...props }: React.HTMLAttributes<HTMLHeadingElement>) {
    return <h2 className={`text-xl font-semibold ${className}`} {...props} />;
}

export function DialogDescription({ className = "", ...props }: React.HTMLAttributes<HTMLParagraphElement>) {
    return <p className={`text-sm text-slate-500 ${className}`} {...props} />;
}

export function DialogFooter({ className = "", ...props }: React.HTMLAttributes<HTMLDivElement>) {
    return <div className={`mt-4 flex flex-wrap justify-end gap-3 ${className}`} {...props} />;
}
