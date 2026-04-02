import { cn } from '@/lib/utils'

/** Shared panel styles for root dropdown and submenu content (matches `UiDropdownMenu`). */
export const dropdownMenuContentClass = cn(
  'z-50 min-w-[8rem] overflow-hidden rounded-md border bg-popover p-1 text-popover-foreground shadow-md outline-none',
  'data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0',
)

/** Styles for `DropdownMenuSubTrigger` aligned with `UiDropdownItem`. */
export const dropdownMenuSubTriggerClass = cn(
  'relative flex cursor-default select-none items-center rounded-sm px-2 py-1.5 text-sm outline-none transition-colors',
  'focus:bg-accent focus:text-accent-foreground data-[state=open]:bg-accent data-[disabled]:pointer-events-none data-[disabled]:opacity-50',
)
