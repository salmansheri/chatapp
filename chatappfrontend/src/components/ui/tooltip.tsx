import { Tooltip as TooltipPrimitive } from "radix-ui";
import type * as React from "react";

import { cn } from "#/lib/utils";

function TooltipProvider(
	props: React.ComponentProps<typeof TooltipPrimitive.Provider>,
) {
	return <TooltipPrimitive.Provider data-slot="tooltip-provider" {...props} />;
}

function Tooltip(props: React.ComponentProps<typeof TooltipPrimitive.Root>) {
	return <TooltipPrimitive.Root data-slot="tooltip" {...props} />;
}

function TooltipTrigger(
	props: React.ComponentProps<typeof TooltipPrimitive.Trigger>,
) {
	return <TooltipPrimitive.Trigger data-slot="tooltip-trigger" {...props} />;
}

function TooltipContent({
	className,
	sideOffset = 8,
	...props
}: React.ComponentProps<typeof TooltipPrimitive.Content>) {
	return (
		<TooltipPrimitive.Portal>
			<TooltipPrimitive.Content
				data-slot="tooltip-content"
				sideOffset={sideOffset}
				className={cn(
					"z-50 rounded-md border border-border/70 bg-popover px-2.5 py-1 text-xs text-popover-foreground shadow-md",
					"data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0",
					className,
				)}
				{...props}
			/>
		</TooltipPrimitive.Portal>
	);
}

export { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger };
