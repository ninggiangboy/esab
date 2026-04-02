import { cn } from '@/lib/utils'

/** Compact dark tooltip (e.g. icon hints, collapsed sidebar). */
export const tooltipInverseContentClass = cn(
  'z-50 max-w-xs overflow-hidden rounded-sm bg-neutral-800 px-2.5 py-1.5 text-xs text-neutral-200',
  'animate-in fade-in-0 data-[state=closed]:animate-out data-[state=closed]:fade-out-0',
)

/** Rich tooltip with panel styling. */
export const tooltipPanelContentClass = cn(
  'z-50 max-w-sm rounded-md border border-border bg-popover px-3 py-2 text-popover-foreground shadow-md',
  'animate-in fade-in-0 data-[state=closed]:animate-out data-[state=closed]:fade-out-0',
)
