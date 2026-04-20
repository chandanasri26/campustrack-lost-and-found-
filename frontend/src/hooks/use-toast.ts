export interface ToastOptions {
    title: string;
    description?: string;
    variant?: "default" | "destructive";
}

export function useToast() {
    const toast = ({ title, description, variant }: ToastOptions) => {
        console.log("Toast:", title, description, variant);
        if (variant === "destructive") {
            window.alert(`${title}: ${description ?? ""}`);
        }
    };

    return { toast };
}
