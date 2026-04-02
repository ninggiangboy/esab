import { cn } from '@/lib/utils'

/**
 * Day triggers for calendar grids. Uses `text-white` on primary fills — this theme’s
 * `primary-foreground` is too close to `primary` for legibility.
 * Today dot is `z-0`; slot content should wrap the day in `relative z-10`.
 */
export const calendarDayCellTriggerClass = cn(
  'isolate relative flex size-8 items-center justify-center rounded-full text-sm tabular-nums text-foreground outline-none',
  'hover:bg-accent hover:text-accent-foreground',
  'focus-visible:ring-2 focus-visible:ring-ring',
  'data-[outside-view]:text-muted-foreground',
  'data-[highlighted]:bg-accent data-[highlighted]:text-accent-foreground',
  'data-[selected]:bg-primary data-[selected]:text-white',
  'data-[selection-start]:bg-primary data-[selection-start]:text-white',
  'data-[selection-end]:bg-primary data-[selection-end]:text-white',
  'data-[unavailable]:pointer-events-none data-[unavailable]:text-muted-foreground data-[unavailable]:line-through',
  'before:pointer-events-none before:absolute before:left-1/2 before:top-1 before:z-0 before:hidden before:size-1 before:-translate-x-1/2 before:rounded-full before:bg-primary before:opacity-70 dark:before:opacity-90',
  'data-[today]:before:block',
  'data-[selected]:before:hidden data-[selection-start]:before:hidden data-[selection-end]:before:hidden',
)
