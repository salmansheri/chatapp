import { AnimatePresence, motion } from "framer-motion";
import {
	EllipsisVerticalIcon,
	SearchIcon,
	UsersIcon,
	XIcon,
} from "lucide-react";

import { Avatar, AvatarFallback, AvatarImage } from "#/components/ui/avatar";
import { Button } from "#/components/ui/button";
import {
	DropdownMenu,
	DropdownMenuContent,
	DropdownMenuItem,
	DropdownMenuTrigger,
} from "#/components/ui/dropdown-menu";
import { Input } from "#/components/ui/input";
import { ScrollArea } from "#/components/ui/scroll-area";
import { Skeleton } from "#/components/ui/skeleton";
import { cn } from "#/lib/utils";

import type { Conversation } from "../types";
import { formatTime, getInitials } from "../utils";

interface ChatSidebarProps {
	conversations: Conversation[];
	isLoading: boolean;
	isMobileOpen: boolean;
	search: string;
	selectedConversationId: string;
	onCloseMobile: () => void;
	onSearchChange: (value: string) => void;
	onSelectConversation: (id: string) => void;
}

const presenceStyles: Record<Conversation["presence"], string> = {
	online: "bg-emerald-400",
	away: "bg-amber-300",
	offline: "bg-slate-500",
	typing: "bg-cyan-400",
};

export function ChatSidebar({
	conversations,
	isLoading,
	isMobileOpen,
	search,
	selectedConversationId,
	onCloseMobile,
	onSearchChange,
	onSelectConversation,
}: ChatSidebarProps) {
	return (
		<AnimatePresence>
			<motion.aside
				initial={{ x: -30, opacity: 0 }}
				animate={{ x: 0, opacity: 1 }}
				exit={{ x: -24, opacity: 0 }}
				className={cn(
					"absolute inset-y-0 left-0 z-40 w-[21.5rem] border-r border-white/10 bg-[#11141d]/95 backdrop-blur-xl lg:static lg:translate-x-0",
					isMobileOpen ? "translate-x-0" : "-translate-x-full lg:translate-x-0",
				)}
			>
				<div className="flex h-16 items-center justify-between border-b border-white/10 px-4">
					<div className="flex items-center gap-3">
						<div className="flex size-9 items-center justify-center rounded-xl bg-gradient-to-br from-cyan-400/90 to-indigo-500/90 shadow-lg shadow-cyan-900/40">
							<UsersIcon className="size-4 text-slate-950" />
						</div>
						<div>
							<p className="text-sm font-semibold text-slate-100">Aster Chat</p>
							<p className="text-xs text-slate-400">Team workspace</p>
						</div>
					</div>

					<div className="flex items-center gap-1">
						<DropdownMenu>
							<DropdownMenuTrigger asChild>
								<Button
									variant="ghost"
									size="icon-sm"
									className="text-slate-400 hover:bg-white/10 hover:text-slate-100"
								>
									<EllipsisVerticalIcon className="size-4" />
									<span className="sr-only">Workspace menu</span>
								</Button>
							</DropdownMenuTrigger>
							<DropdownMenuContent align="end">
								<DropdownMenuItem>New channel</DropdownMenuItem>
								<DropdownMenuItem>Invite people</DropdownMenuItem>
								<DropdownMenuItem>Notification settings</DropdownMenuItem>
							</DropdownMenuContent>
						</DropdownMenu>

						<Button
							variant="ghost"
							size="icon-sm"
							className="text-slate-400 hover:bg-white/10 hover:text-slate-100 lg:hidden"
							onClick={onCloseMobile}
						>
							<XIcon className="size-4" />
						</Button>
					</div>
				</div>

				<div className="p-3">
					<div className="relative">
						<SearchIcon className="pointer-events-none absolute top-1/2 left-3 size-4 -translate-y-1/2 text-slate-500" />
						<Input
							value={search}
							onChange={(event) => onSearchChange(event.target.value)}
							placeholder="Search messages"
							className="h-10 border-white/10 bg-[#171b27] pl-9 text-slate-200 placeholder:text-slate-500"
						/>
					</div>
				</div>

				<ScrollArea className="h-[calc(100dvh-7.5rem)] px-2 pb-3">
					<div className="space-y-1">
						{isLoading ? (
							<ConversationSkeletonList />
						) : conversations.length === 0 ? (
							<p className="px-3 py-8 text-center text-sm text-slate-500">
								No conversations match your search.
							</p>
						) : (
							conversations.map((conversation) => {
								const isActive = selectedConversationId === conversation.id;
								return (
									<button
										key={conversation.id}
										type="button"
										onClick={() => onSelectConversation(conversation.id)}
										className={cn(
											"group flex w-full items-center gap-3 rounded-xl px-3 py-2.5 text-left transition",
											isActive
												? "bg-[#1f2535] ring-1 ring-cyan-400/30"
												: "hover:bg-white/6",
										)}
									>
										<div className="relative">
											<Avatar className="size-10 border border-white/10">
												<AvatarImage
													src={conversation.avatarUrl}
													alt={conversation.name}
												/>
												<AvatarFallback className="bg-[#23293b] text-slate-300">
													{getInitials(conversation.name)}
												</AvatarFallback>
											</Avatar>
											<span
												className={cn(
													"absolute right-0 bottom-0 block size-2.5 rounded-full ring-2 ring-[#11141d]",
													presenceStyles[conversation.presence],
												)}
											/>
										</div>

										<div className="min-w-0 flex-1">
											<div className="flex items-center justify-between gap-2">
												<p className="truncate text-sm font-medium text-slate-100">
													{conversation.name}
												</p>
												<span className="text-xs text-slate-500">
													{formatTime(conversation.lastMessageAt)}
												</span>
											</div>
											<div className="mt-0.5 flex items-center gap-2">
												<p className="line-clamp-1 text-xs text-slate-400">
													{conversation.lastMessage}
												</p>
												{conversation.unreadCount > 0 ? (
													<span className="ml-auto inline-flex min-w-5 items-center justify-center rounded-full bg-cyan-400 px-1.5 text-[11px] font-semibold text-slate-950">
														{conversation.unreadCount}
													</span>
												) : null}
											</div>
										</div>
									</button>
								);
							})
						)}
					</div>
				</ScrollArea>
			</motion.aside>
		</AnimatePresence>
	);
}

function ConversationSkeletonList() {
	return ["a", "b", "c", "d", "e", "f", "g", "h"].map((token) => (
		<div key={token} className="flex items-center gap-3 rounded-xl px-3 py-2.5">
			<Skeleton className="size-10 rounded-full bg-slate-700/60" />
			<div className="flex-1 space-y-2">
				<Skeleton className="h-3 w-24 bg-slate-700/55" />
				<Skeleton className="h-2.5 w-40 bg-slate-700/45" />
			</div>
		</div>
	));
}
