import { useEffect, useMemo, useState } from "react";

import { chatDataset } from "../mock-data";
import type { Conversation, Message } from "../types";

const formatPreview = (value: string) => value.trim().slice(0, 120);

export function useChatState() {
	const [isLoading, setIsLoading] = useState(true);
	const [search, setSearch] = useState("");
	const [isMobileSidebarOpen, setMobileSidebarOpen] = useState(false);
	const [selectedConversationId, setSelectedConversationId] = useState(
		chatDataset.conversations[0]?.id ?? "",
	);
	const [conversations, setConversations] = useState<Conversation[]>(
		chatDataset.conversations,
	);
	const [messages, setMessages] = useState<Message[]>(chatDataset.messages);
	const [draft, setDraft] = useState("");

	useEffect(() => {
		const timer = window.setTimeout(() => setIsLoading(false), 650);
		return () => window.clearTimeout(timer);
	}, []);

	const filteredConversations = useMemo(() => {
		if (!search.trim()) {
			return conversations;
		}
		const needle = search.toLowerCase();
		return conversations.filter((conversation) => {
			return (
				conversation.name.toLowerCase().includes(needle) ||
				conversation.lastMessage.toLowerCase().includes(needle)
			);
		});
	}, [conversations, search]);

	const selectedConversation = useMemo(
		() =>
			conversations.find(
				(conversation) => conversation.id === selectedConversationId,
			),
		[conversations, selectedConversationId],
	);

	const selectedMessages = useMemo(
		() =>
			messages.filter(
				(message) => message.conversationId === selectedConversationId,
			),
		[messages, selectedConversationId],
	);

	const typingText = useMemo(() => {
		if (!selectedConversation) {
			return "";
		}
		return selectedConversation.presence === "typing"
			? `${selectedConversation.name} is typing...`
			: "";
	}, [selectedConversation]);

	const selectConversation = (conversationId: string) => {
		setSelectedConversationId(conversationId);
		setMobileSidebarOpen(false);
		setConversations((current) =>
			current.map((conversation) =>
				conversation.id === conversationId
					? { ...conversation, unreadCount: 0 }
					: conversation,
			),
		);
	};

	const sendMessage = () => {
		if (!selectedConversationId || !draft.trim()) {
			return;
		}

		const nextMessage: Message = {
			id: `m-${crypto.randomUUID()}`,
			conversationId: selectedConversationId,
			senderId: chatDataset.me.id,
			kind: "text",
			content: draft.trim(),
			timestamp: new Date().toISOString(),
			deliveryState: "sent",
		};

		setMessages((current) => [...current, nextMessage]);
		setConversations((current) =>
			current.map((conversation) =>
				conversation.id === selectedConversationId
					? {
							...conversation,
							lastMessage: formatPreview(nextMessage.content),
							lastMessageAt: nextMessage.timestamp,
						}
					: conversation,
			),
		);
		setDraft("");

		window.setTimeout(() => {
			setMessages((current) =>
				current.map((message) =>
					message.id === nextMessage.id
						? { ...message, deliveryState: "delivered" }
						: message,
				),
			);
		}, 900);
	};

	const deleteMessage = (messageId: string) => {
		setMessages((current) =>
			current.filter((message) => message.id !== messageId),
		);
	};

	return {
		draft,
		filteredConversations,
		isLoading,
		isMobileSidebarOpen,
		search,
		selectedConversation,
		selectedConversationId,
		selectedMessages,
		typingText,
		deleteMessage,
		selectConversation,
		sendMessage,
		setDraft,
		setMobileSidebarOpen,
		setSearch,
	};
}
