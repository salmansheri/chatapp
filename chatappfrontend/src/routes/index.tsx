import { createFileRoute } from "@tanstack/react-router";

import { ChatApp } from "#/features/chat/chat-app";

export const Route = createFileRoute("/")({
	component: ChatApp,
});
