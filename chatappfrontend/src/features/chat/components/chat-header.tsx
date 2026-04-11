import {
	BellIcon,
	InfoIcon,
	PhoneIcon,
	SearchIcon,
	VideoIcon,
} from "lucide-react";
import type { ReactNode } from "react";

import { Avatar, AvatarFallback, AvatarImage } from "#/components/ui/avatar";
import { Button } from "#/components/ui/button";
import {
	Tooltip,
	TooltipContent,
	TooltipProvider,
	TooltipTrigger,
} from "#/components/ui/tooltip";

import type { Conversation } from "../types";
import { getInitials } from "../utils";

interface ChatHeaderProps {
	conversation: Conversation;
	onOpenMobileSidebar: () => void;
}

const statusLabel: Record<Conversation["presence"], string> = {
	online: "Online now",
	away: "Away",
	offline: "Offline",
	typing: "Typing...",
};

export function ChatHeader({
	conversation,
	onOpenMobileSidebar,
}: ChatHeaderProps) {
	return (
		<header className="flex h-16 items-center border-b border-white/10 bg-[#151a27]/90 px-4 backdrop-blur-xl sm:px-6">
			<Button
				variant="ghost"
				size="icon-sm"
				onClick={onOpenMobileSidebar}
				className="mr-2 text-slate-300 hover:bg-white/10 lg:hidden"
			>
				<SearchIcon className="size-4" />
				<span className="sr-only">Open conversations</span>
			</Button>

			<Avatar className="size-10 border border-white/10">
				<AvatarImage src={conversation.avatarUrl} alt={conversation.name} />
				<AvatarFallback className="bg-[#242b3f] text-slate-300">
					{getInitials(conversation.name)}
				</AvatarFallback>
			</Avatar>

			<div className="ml-3 min-w-0">
				<p className="truncate text-sm font-semibold text-slate-100">
					{conversation.name}
				</p>
				<p className="text-xs text-slate-400">
					{statusLabel[conversation.presence]}
				</p>
			</div>

			<div className="ml-auto flex items-center gap-1">
				<TooltipProvider delayDuration={200}>
					<HeaderIconButton
						icon={<PhoneIcon className="size-4" />}
						label="Voice call"
					/>
					<HeaderIconButton
						icon={<VideoIcon className="size-4" />}
						label="Video call"
					/>
					<HeaderIconButton
						icon={<BellIcon className="size-4" />}
						label="Mute chat"
					/>
					<HeaderIconButton
						icon={<InfoIcon className="size-4" />}
						label="Conversation info"
					/>
				</TooltipProvider>
			</div>
		</header>
	);
}

function HeaderIconButton({ icon, label }: { icon: ReactNode; label: string }) {
	return (
		<Tooltip>
			<TooltipTrigger asChild>
				<Button
					variant="ghost"
					size="icon-sm"
					className="text-slate-300 hover:bg-white/10 hover:text-slate-100"
				>
					{icon}
				</Button>
			</TooltipTrigger>
			<TooltipContent>{label}</TooltipContent>
		</Tooltip>
	);
}
