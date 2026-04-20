import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardFooter, CardHeader } from "@/components/ui/card";
import { MapPin, Clock, User, Package } from "lucide-react";
import { formatDistanceToNow } from "date-fns";

export interface Item {
    id: string;
    title: string;
    description: string;
    category: string;
    type: "lost" | "found";
    status: "open" | "resolved";
    approved: boolean;
    location: string;
    imageUrl: string | null;
    userId: string;
    userName: string;
    userEmail: string;
    createdAt: string;
    updatedAt: string;
}

interface ItemCardProps {
    item: Item;
    onEdit?: (item: Item) => void;
    onDelete?: (id: string) => void;
    onResolve?: (id: string) => void;
    onApprove?: (id: string) => void;
    onView?: (item: Item) => void;
    onMessage?: (item: Item) => void;
    isOwner?: boolean;
    isAdmin?: boolean;
}

export default function ItemCard({
    item,
    onEdit,
    onDelete,
    onResolve,
    onApprove,
    onView,
    onMessage,
    isOwner,
    isAdmin,
}: ItemCardProps) {
    const canManage = isOwner || isAdmin;

    return (
        <Card
            className={`flex flex-col overflow-hidden transition-shadow duration-200 ${
                onView ? "cursor-pointer hover:shadow-md" : ""
            }`}
            onClick={onView ? () => onView(item) : undefined}
        >
            {item.imageUrl && (
                <div className="relative h-44 overflow-hidden bg-muted">
                    <img
                        src={item.imageUrl}
                        alt={item.title}
                        className="w-full h-full object-cover"
                    />
                </div>
            )}
            <CardHeader className="pb-2 pt-4 px-4">
                <div className="flex items-start justify-between gap-2">
                    <h3 className="font-semibold text-foreground line-clamp-1 flex-1">{item.title}</h3>
                    <div className="flex gap-1 shrink-0 items-center">
                        <Badge
                            variant={item.type === "lost" ? "destructive" : "default"}
                            className="text-xs uppercase"
                        >
                            {item.type}
                        </Badge>
                        {!item.approved && isAdmin && (
                            <Badge variant="secondary" className="text-xs">Pending</Badge>
                        )}
                        {item.status === "resolved" && (
                            <Badge variant="secondary" className="text-xs">Resolved</Badge>
                        )}
                    </div>
                </div>
            </CardHeader>
            <CardContent className="px-4 pb-3 flex-1">
                <p className="text-sm text-muted-foreground line-clamp-2 mb-3">{item.description}</p>
                <div className="flex flex-col gap-1.5">
                    <div className="flex items-center gap-1.5 text-xs text-muted-foreground">
                        <Package className="h-3.5 w-3.5 shrink-0" />
                        <span>{item.category}</span>
                    </div>
                    <div className="flex items-center gap-1.5 text-xs text-muted-foreground">
                        <MapPin className="h-3.5 w-3.5 shrink-0" />
                        <span className="line-clamp-1">{item.location}</span>
                    </div>
                    <div className="flex items-center gap-1.5 text-xs text-muted-foreground">
                        <User className="h-3.5 w-3.5 shrink-0" />
                        <span>{item.userName}</span>
                    </div>
                    <div className="flex items-center gap-1.5 text-xs text-muted-foreground">
                        <Clock className="h-3.5 w-3.5 shrink-0" />
                        <span>{formatDistanceToNow(new Date(item.createdAt), { addSuffix: true })}</span>
                    </div>
                </div>
            </CardContent>
            {canManage && (
                <CardFooter className="px-4 pb-4 pt-0 flex gap-2">
                    {onApprove && !item.approved && isAdmin && (
                        <Button size="sm" variant="outline" onClick={() => onApprove(item.id)} className="flex-1 text-xs">
                            Approve
                        </Button>
                    )}
                    {onResolve && item.status === "open" && (
                        <Button size="sm" variant="outline" onClick={() => onResolve(item.id)} className="flex-1 text-xs">
                            Mark Resolved
                        </Button>
                    )}
                    {onEdit && isOwner && (
                        <Button size="sm" variant="outline" onClick={() => onEdit(item)} className="flex-1 text-xs">
                            Edit
                        </Button>
                    )}
                    {onDelete && (
                        <Button
                            size="sm"
                            variant="outline"
                            onClick={() => onDelete(item.id)}
                            className="text-xs text-destructive hover:text-destructive border-destructive/30 hover:border-destructive"
                        >
                            Delete
                        </Button>
                    )}
                </CardFooter>
            )}
            {!isOwner && onMessage && (
                <CardFooter className="px-4 pb-4 pt-0">
                    <Button
                        size="sm"
                        className="w-full text-xs bg-sky-600 text-white hover:bg-sky-700"
                        onClick={(e) => {
                            e.stopPropagation();
                            onMessage(item);
                        }}
                    >
                        Message Owner
                    </Button>
                </CardFooter>
            )}
        </Card>
    );
}
