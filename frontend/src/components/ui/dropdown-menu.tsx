import React, { createContext, useContext, useState } from "react";

interface DropdownContextValue {
    open: boolean;
    toggle: () => void;
    close: () => void;
}

const DropdownContext = createContext<DropdownContextValue | null>(null);

export function DropdownMenu({ children }: { children: React.ReactNode }) {
    const [open, setOpen] = useState(false);

    const value = {
        open,
        toggle: () => setOpen((prev) => !prev),
        close: () => setOpen(false),
    };

    return <DropdownContext.Provider value={value}>{children}</DropdownContext.Provider>;
}

export function DropdownMenuTrigger({ asChild, children }: { asChild?: boolean; children: React.ReactNode }) {
    const ctx = useContext(DropdownContext);
    if (!ctx) return null;

    const child = React.Children.only(children) as React.ReactElement;
    return React.cloneElement(child, {
        onClick: (event: React.MouseEvent) => {
            event.stopPropagation();
            child.props.onClick?.(event);
            ctx.toggle();
        },
    });
}

export function DropdownMenuContent({ align = "start", className = "", children }: { align?: string; className?: string; children: React.ReactNode }) {
    const ctx = useContext(DropdownContext);
    if (!ctx || !ctx.open) return null;
    return (
        <div className={`absolute top-full right-0 z-50 mt-2 w-56 rounded-xl border border-slate-200 bg-white p-2 shadow-lg ${className}`}>
            {children}
        </div>
    );
}

export function DropdownMenuItem({ className = "", children, onClick }: { className?: string; children: React.ReactNode; onClick?: () => void }) {
    const ctx = useContext(DropdownContext);
    return (
        <button
            type="button"
            className={`w-full rounded-lg px-3 py-2 text-left text-sm text-slate-700 hover:bg-slate-100 ${className}`}
            onClick={() => {
                onClick?.();
                ctx?.close();
            }}
        >
            {children}
        </button>
    );
}

export function DropdownMenuSeparator() {
    return <div className="my-2 h-px bg-slate-200" />;
}
