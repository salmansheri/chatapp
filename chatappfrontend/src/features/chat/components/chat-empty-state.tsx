import { MessageCircleOffIcon } from "lucide-react";

import { Button } from "#/components/ui/button";

interface ChatEmptyStateProps {
	onResetSearch: () => void;
}

export function ChatEmptyState({ onResetSearch }: ChatEmptyStateProps) {
	return (
		<div className="flex h-[calc(100dvh-4rem)] items-center justify-center px-6">
			<div className="max-w-md rounded-2xl border border-white/10 bg-[#161c2a] p-8 text-center shadow-2xl shadow-black/25">
				<div className="mx-auto mb-4 flex size-12 items-center justify-center rounded-xl bg-cyan-500/15 text-cyan-300">
					<MessageCircleOffIcon className="size-6" />
				</div>
				<h2 className="text-lg font-semibold text-slate-100">
					No conversation selected
				</h2>
				<p className="mt-2 text-sm text-slate-400">
					Choose a chat from the sidebar to start messaging, or clear filters to
					see more results.
				</p>
				<Button
					onClick={onResetSearch}
					className="mt-5 bg-cyan-500 text-slate-950 hover:bg-cyan-400"
				>
					Reset search
				</Button>
			</div>
		</div>
	);
}
