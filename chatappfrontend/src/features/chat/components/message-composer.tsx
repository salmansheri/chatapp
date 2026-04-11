import { PaperclipIcon, SendHorizontalIcon, SmileIcon } from "lucide-react";
import { type FormEventHandler, useState } from "react";

import { Button } from "#/components/ui/button";
import {
	Dialog,
	DialogContent,
	DialogDescription,
	DialogFooter,
	DialogHeader,
	DialogTitle,
	DialogTrigger,
} from "#/components/ui/dialog";
import { Input } from "#/components/ui/input";
import {
	Tooltip,
	TooltipContent,
	TooltipProvider,
	TooltipTrigger,
} from "#/components/ui/tooltip";

interface MessageComposerProps {
	draft: string;
	onDraftChange: (value: string) => void;
	onSend: () => void;
}

export function MessageComposer({
	draft,
	onDraftChange,
	onSend,
}: MessageComposerProps) {
	const [isAttachmentOpen, setAttachmentOpen] = useState(false);

	const onSubmit: FormEventHandler<HTMLFormElement> = (event) => {
		event.preventDefault();
		onSend();
	};

	return (
		<footer className="sticky bottom-0 border-t border-white/10 bg-[#151a27]/95 px-3 py-3 backdrop-blur-xl sm:px-6">
			<form
				onSubmit={onSubmit}
				className="mx-auto flex max-w-4xl items-center gap-2"
			>
				<TooltipProvider delayDuration={150}>
					<Dialog open={isAttachmentOpen} onOpenChange={setAttachmentOpen}>
						<Tooltip>
							<TooltipTrigger asChild>
								<DialogTrigger asChild>
									<Button
										type="button"
										variant="ghost"
										size="icon-sm"
										className="text-slate-300 hover:bg-white/10"
									>
										<PaperclipIcon className="size-4" />
									</Button>
								</DialogTrigger>
							</TooltipTrigger>
							<TooltipContent>Add attachment</TooltipContent>
						</Tooltip>

						<DialogContent className="border-white/10 bg-[#141b29] text-slate-100">
							<DialogHeader>
								<DialogTitle>Attach a file</DialogTitle>
								<DialogDescription>
									Upload support is wired in backend APIs. This UI keeps things
									mocked for now.
								</DialogDescription>
							</DialogHeader>

							<div className="grid gap-2">
								<Button
									variant="outline"
									className="justify-start border-white/15 bg-white/5"
								>
									Choose from computer
								</Button>
								<Button
									variant="outline"
									className="justify-start border-white/15 bg-white/5"
								>
									Share design link
								</Button>
							</div>

							<DialogFooter>
								<Button
									type="button"
									variant="secondary"
									onClick={() => setAttachmentOpen(false)}
								>
									Close
								</Button>
							</DialogFooter>
						</DialogContent>
					</Dialog>

					<Tooltip>
						<TooltipTrigger asChild>
							<Button
								type="button"
								variant="ghost"
								size="icon-sm"
								className="text-slate-300 hover:bg-white/10"
							>
								<SmileIcon className="size-4" />
							</Button>
						</TooltipTrigger>
						<TooltipContent>Emoji</TooltipContent>
					</Tooltip>
				</TooltipProvider>

				<Input
					value={draft}
					onChange={(event) => onDraftChange(event.target.value)}
					placeholder="Type a message"
					className="h-11 border-white/10 bg-[#0f1420] text-slate-100 placeholder:text-slate-500"
				/>

				<Button
					type="submit"
					size="icon"
					className="h-11 w-11 rounded-xl bg-cyan-500 text-slate-950 hover:bg-cyan-400"
				>
					<SendHorizontalIcon className="size-4" />
					<span className="sr-only">Send message</span>
				</Button>
			</form>
		</footer>
	);
}
