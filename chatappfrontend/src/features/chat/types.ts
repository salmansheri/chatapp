export type Presence = "online" | "away" | "offline" | "typing";

export type DeliveryState = "sending" | "sent" | "delivered" | "read";

export type MessageKind = "text" | "emoji" | "attachment";

export interface Conversation {
	id: string;
	name: string;
	avatarUrl?: string;
	isGroup: boolean;
	presence: Presence;
	lastMessage: string;
	lastMessageAt: string;
	unreadCount: number;
}

export interface Message {
	id: string;
	conversationId: string;
	senderId: string;
	kind: MessageKind;
	content: string;
	timestamp: string;
	deliveryState?: DeliveryState;
	attachmentName?: string;
	replyToId?: string;
}

export interface ChatUser {
	id: string;
	name: string;
	avatarUrl?: string;
}

export interface ChatDataset {
	me: ChatUser;
	participants: Record<string, ChatUser>;
	conversations: Conversation[];
	messages: Message[];
}
