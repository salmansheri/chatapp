import { AnimatePresence, motion } from "framer-motion";
import {
	CheckCheckIcon,
	CheckIcon,
	Clock3Icon,
	DownloadIcon,
	ReplyIcon,
	SmilePlusIcon,
	Trash2Icon,
} from "lucide-react";

import { Avatar, AvatarFallback, AvatarImage } from "#/components/ui/avatar";
import { Button } from "#/components/ui/button";
import {
	DropdownMenu,
	DropdownMenuContent,
	DropdownMenuItem,
	DropdownMenuTrigger,
} from "#/components/ui/dropdown-menu";
import { ScrollArea } from "#/components/ui/scroll-area";
import { Skeleton } from "#/components/ui/skeleton";
import {
	Tooltip,
	TooltipContent,
	TooltipProvider,
	TooltipTrigger,
} from "#/components/ui/tooltip";
import { cn } from "#/lib/utils";

import type { ChatUser, Message } from "../types";
import { formatDateLabel, formatTime, getInitials } from "../utils";

interface MessageListProps {
	isLoading: boolean;
	meId: string;
	messages: Message[];
	participants: Record<string, ChatUser>;
	typingText: string;
	onDeleteMessage: (id: string) => void;
}

export function MessageList({
	isLoading,
	meId,
	messages,
	participants,
	typingText,
	onDeleteMessage,
}: MessageListProps) {
	const grouped = groupMessagesByDate(messages);

	if (isLoading) {
		return <MessageSkeleton />;
	}

	return (
		<ScrollArea className="h-[calc(100dvh-9.5rem)] px-3 py-4 sm:px-6">
			<div className="mx-auto flex w-full max-w-4xl flex-col gap-4">
				<TooltipProvider delayDuration={150}>
					{grouped.map((group) => (
						<section key={group.label} className="space-y-4">
							<div className="flex items-center justify-center">
								<span className="rounded-full border border-white/12 bg-[#1a2030] px-3 py-1 text-[11px] font-medium tracking-wide text-slate-400 uppercase">
									{group.label}
								</span>
							</div>

							<AnimatePresence initial={false}>
								{group.items.map((message) => {
									const isMe = message.senderId === meId;
									const author = participants[message.senderId];
									return (
										<motion.article
											key={message.id}
											layout
											initial={{ opacity: 0, y: 10 }}
											animate={{ opacity: 1, y: 0 }}
											exit={{ opacity: 0, y: -8 }}
											className={cn(
												"group flex items-end gap-2",
												isMe ? "justify-end" : "justify-start",
											)}
										>
											{isMe ? null : (
												<Avatar className="mb-5 size-8 border border-white/10">
													<AvatarImage
														src={author?.avatarUrl}
														alt={author?.name}
													/>
													<AvatarFallback className="bg-[#2a3144] text-[11px] text-slate-300">
														{getInitials(author?.name ?? "Unknown")}
													</AvatarFallback>
												</Avatar>
											)}

											<div className="relative max-w-[82%] sm:max-w-[70%]">
												<MessageActions
													messageId={message.id}
													onDeleteMessage={onDeleteMessage}
												/>
												<MessageBubble message={message} isMe={isMe} />
												<div
													className={cn(
														"mt-1 flex items-center gap-1 px-1 text-[11px]",
														isMe
															? "justify-end text-slate-500"
															: "justify-start text-slate-500",
													)}
												>
													<span>{formatTime(message.timestamp)}</span>
													{isMe ? (
														<DeliveryIcon state={message.deliveryState} />
													) : null}
												</div>
											</div>
										</motion.article>
									);
								})}
							</AnimatePresence>
						</section>
					))}

					{typingText ? (
						<div className="max-w-fit rounded-2xl rounded-bl-sm border border-white/12 bg-[#1a2131] px-4 py-2 text-sm text-slate-300">
							<span className="inline-flex items-center gap-1">
								{typingText}
								<span className="animate-bounce [animation-delay:100ms]">
									.
								</span>
								<span className="animate-bounce [animation-delay:220ms]">
									.
								</span>
								<span className="animate-bounce [animation-delay:320ms]">
									.
								</span>
							</span>
						</div>
					) : null}
				</TooltipProvider>
			</div>
		</ScrollArea>
	);
}

function MessageBubble({ message, isMe }: { message: Message; isMe: boolean }) {
	return (
		<div
			className={cn(
				"rounded-2xl px-3.5 py-2.5 text-sm leading-relaxed shadow-[0_12px_24px_rgba(0,0,0,0.18)]",
				isMe
					? "rounded-br-sm bg-gradient-to-br from-cyan-500 to-sky-500 text-slate-950"
					: "rounded-bl-sm border border-white/10 bg-[#1b2233] text-slate-100",
			)}
		>
			{message.kind === "attachment" ? (
				<div className="space-y-2">
					<p>{message.content}</p>
					<div
						className={cn(
							"flex items-center justify-between rounded-lg px-3 py-2",
							isMe ? "bg-cyan-950/20" : "bg-white/6",
						)}
					>
						<span className="max-w-[12rem] truncate text-xs font-medium">
							{message.attachmentName}
						</span>
						<Button
							variant="ghost"
							size="icon-xs"
							className={cn(
								"rounded-full",
								isMe ? "hover:bg-cyan-950/25" : "hover:bg-white/10",
							)}
						>
							<DownloadIcon className="size-3.5" />
						</Button>
					</div>
				</div>
			) : (
				<p>{message.content}</p>
			)}
		</div>
	);
}

function MessageActions({
	messageId,
	onDeleteMessage,
}: {
	messageId: string;
	onDeleteMessage: (id: string) => void;
}) {
	return (
		<div className="pointer-events-none absolute -top-8 right-0 z-10 flex items-center gap-1 rounded-lg border border-white/10 bg-[#151b29]/95 p-1 opacity-0 shadow-lg transition group-hover:pointer-events-auto group-hover:opacity-100">
			<Tooltip>
				<TooltipTrigger asChild>
					<Button variant="ghost" size="icon-xs" className="hover:bg-white/8">
						<ReplyIcon className="size-3.5" />
					</Button>
				</TooltipTrigger>
				<TooltipContent>Reply</TooltipContent>
			</Tooltip>

			<Tooltip>
				<TooltipTrigger asChild>
					<Button variant="ghost" size="icon-xs" className="hover:bg-white/8">
						<SmilePlusIcon className="size-3.5" />
					</Button>
				</TooltipTrigger>
				<TooltipContent>React</TooltipContent>
			</Tooltip>

			<DropdownMenu>
				<DropdownMenuTrigger asChild>
					<Button variant="ghost" size="icon-xs" className="hover:bg-white/8">
						<Trash2Icon className="size-3.5" />
					</Button>
				</DropdownMenuTrigger>
				<DropdownMenuContent align="end">
					<DropdownMenuItem onClick={() => onDeleteMessage(messageId)}>
						Delete message
					</DropdownMenuItem>
				</DropdownMenuContent>
			</DropdownMenu>
		</div>
	);
}

function DeliveryIcon({ state }: { state?: Message["deliveryState"] }) {
	if (!state) {
		return null;
	}

	if (state === "sending") {
		return <Clock3Icon className="size-3.5" />;
	}

	if (state === "read") {
		return <CheckCheckIcon className="size-3.5 text-cyan-400" />;
	}

	if (state === "delivered") {
		return <CheckCheckIcon className="size-3.5" />;
	}

	return <CheckIcon className="size-3.5" />;
}

function MessageSkeleton() {
	return (
		<div className="h-[calc(100dvh-9.5rem)] space-y-4 px-6 py-5">
			<Skeleton className="mx-auto h-5 w-24 rounded-full bg-slate-700/45" />
			<Skeleton className="h-12 w-2/3 rounded-2xl bg-slate-700/50" />
			<Skeleton className="ml-auto h-16 w-1/2 rounded-2xl bg-slate-700/55" />
			<Skeleton className="h-12 w-3/5 rounded-2xl bg-slate-700/50" />
			<Skeleton className="ml-auto h-10 w-1/3 rounded-2xl bg-slate-700/55" />
		</div>
	);
}

function groupMessagesByDate(messages: Message[]) {
	const groups = new Map<string, Message[]>();

	for (const message of messages) {
		const key = formatDateLabel(message.timestamp);
		if (!groups.has(key)) {
			groups.set(key, []);
		}
		const bucket = groups.get(key);
		if (bucket) {
			bucket.push(message);
		}
	}

	return Array.from(groups.entries()).map(([label, items]) => ({
		label,
		items,
	}));
}
