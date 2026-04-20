import React, { createContext, useContext, useState } from "react";

interface SelectContextValue {
    value: string;
    onValueChange: (value: string) => void;
    open: boolean;
    setOpen: (open: boolean) => void;
}

const SelectContext = createContext<SelectContextValue | null>(null);

interface SelectProps extends React.HTMLAttributes<HTMLDivElement> {
    value: string;
    onValueChange: (value: string) => void;
    children: React.ReactNode;
    className?: string;
}

export function Select({ value, onValueChange, children, className = "", ...props }: SelectProps) {
    const [open, setOpen] = useState(false);

    return (
        <SelectContext.Provider value={{ value, onValueChange, open, setOpen }}>
            <div className={`relative ${className}`} {...props}>
                {children}
            </div>
        </SelectContext.Provider>
    );
}

export function SelectTrigger({ className = "", children }: { className?: string; children: React.ReactNode }) {
    const ctx = useContext(SelectContext);
    if (!ctx) return null;

    return (
        <button
            type="button"
            className={`w-full rounded-md border border-slate-300 bg-white px-3 py-2 text-left text-sm text-slate-900 shadow-sm transition focus:outline-none focus:ring-2 focus:ring-primary/10 ${className}`}
            onClick={() => ctx.setOpen(!ctx.open)}
        >
            {children}
        </button>
    );
}

export function SelectValue({ placeholder = "Select...", className = "" }: { placeholder?: string; className?: string }) {
    const ctx = useContext(SelectContext);
    if (!ctx) return null;
    return <span className={className}>{ctx.value || placeholder}</span>;
}

export function SelectContent({ className = "", children }: { className?: string; children: React.ReactNode }) {
    const ctx = useContext(SelectContext);
    if (!ctx || !ctx.open) return null;
    return (
        <div className={`absolute z-10 mt-2 w-full rounded-xl border border-slate-200 bg-white shadow-lg ${className}`}>
            {children}
        </div>
    );
}

export function SelectItem({ value, children, className = "" }: { value: string; children: React.ReactNode; className?: string }) {
    const ctx = useContext(SelectContext);
    if (!ctx) return null;
    return (
        <button
            type="button"
            className={`w-full text-left px-3 py-2 text-sm text-slate-900 hover:bg-slate-100 ${className}`}
            onClick={() => {
                ctx.onValueChange(value);
                ctx.setOpen(false);
            }}
        >
            {children}
        </button>
    );
}
