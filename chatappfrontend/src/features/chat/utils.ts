export const formatTime = (iso: string) =>
	new Intl.DateTimeFormat([], {
		hour: "numeric",
		minute: "2-digit",
	}).format(new Date(iso));

export const formatDateLabel = (iso: string) => {
	const value = new Date(iso);
	const today = new Date();
	const yesterday = new Date();
	yesterday.setDate(today.getDate() - 1);

	const sameDay =
		value.getFullYear() === today.getFullYear() &&
		value.getMonth() === today.getMonth() &&
		value.getDate() === today.getDate();

	if (sameDay) {
		return "Today";
	}

	const isYesterday =
		value.getFullYear() === yesterday.getFullYear() &&
		value.getMonth() === yesterday.getMonth() &&
		value.getDate() === yesterday.getDate();

	if (isYesterday) {
		return "Yesterday";
	}

	return new Intl.DateTimeFormat([], {
		month: "short",
		day: "numeric",
		year: "numeric",
	}).format(value);
};

export const getInitials = (name: string) => {
	const parts = name.split(" ").filter(Boolean);
	if (parts.length === 0) {
		return "?";
	}
	if (parts.length === 1) {
		return parts[0].slice(0, 2).toUpperCase();
	}
	const first = parts[0]?.[0] ?? "?";
	const last = parts[parts.length - 1]?.[0] ?? "?";
	return `${first}${last}`.toUpperCase();
};
