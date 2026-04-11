import { motion } from "framer-motion";
import { ChatEmptyState } from "./components/chat-empty-state";
import { ChatHeader } from "./components/chat-header";
import { ChatSidebar } from "./components/chat-sidebar";
import { MessageComposer } from "./components/message-composer";
import { MessageList } from "./components/message-list";
import { useChatState } from "./hooks/use-chat-state";
import { chatDataset } from "./mock-data";

export function ChatApp() {
	const {
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
	} = useChatState();

	return (
		<div className="relative flex h-dvh overflow-hidden bg-[#0f131d] text-slate-100">
			<ChatSidebar
				conversations={filteredConversations}
				isLoading={isLoading}
				isMobileOpen={isMobileSidebarOpen}
				search={search}
				selectedConversationId={selectedConversationId}
				onCloseMobile={() => setMobileSidebarOpen(false)}
				onSearchChange={setSearch}
				onSelectConversation={selectConversation}
			/>

			{isMobileSidebarOpen ? (
				<button
					type="button"
					onClick={() => setMobileSidebarOpen(false)}
					className="absolute inset-0 z-30 bg-black/50 backdrop-blur-[1px] lg:hidden"
					aria-label="Close sidebar"
				/>
			) : null}

			<motion.main
				layout
				className="relative flex min-w-0 flex-1 flex-col bg-[radial-gradient(circle_at_top_left,rgba(56,189,248,0.11),transparent_38%),linear-gradient(180deg,#101622_0%,#0f141f_100%)]"
			>
				{selectedConversation ? (
					<>
						<ChatHeader
							conversation={selectedConversation}
							onOpenMobileSidebar={() => setMobileSidebarOpen(true)}
						/>
						<MessageList
							isLoading={isLoading}
							meId={chatDataset.me.id}
							messages={selectedMessages}
							participants={chatDataset.participants}
							typingText={typingText}
							onDeleteMessage={deleteMessage}
						/>
						<MessageComposer
							draft={draft}
							onDraftChange={setDraft}
							onSend={sendMessage}
						/>
					</>
				) : (
					<ChatEmptyState onResetSearch={() => setSearch("")} />
				)}
			</motion.main>
		</div>
	);
}
