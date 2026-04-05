/**
 * Generates docs/content/ui/*.md mirroring frontend-sample ComponentPreview names
 * (React examples under apps/docs/shared/components/examples/*.tsx).
 */
import fs from 'node:fs'
import path from 'node:path'
import { fileURLToPath } from 'node:url'

const __dirname = path.dirname(fileURLToPath(import.meta.url))
const contentDir = path.join(__dirname, '../src/docs/content/ui')

/** slug -> PascalCase prefix used in example file names */
const PREFIX = {
  sidebar: 'Sidebar',
  accordion: 'Accordion',
  avatar: 'Avatar',
  separator: 'Separator',
  button: 'Button',
  'file-trigger': 'FileTrigger',
  table: 'Table',
  'data-table': 'DataTable',
  pagination: 'Pagination',
  'search-field': 'Searchfield',
  calendar: 'Calendar',
  'range-calendar': 'RangeCalendar',
  'date-picker': 'DatePicker',
  'date-range-picker': 'DateRangePicker',
  'date-field': 'DateField',
  'time-field': 'TimeField',
  input: 'Input',
  textarea: 'TextArea',
  'number-field': 'NumberField',
  checkbox: 'Checkbox',
  'radio-group': 'RadioGroup',
  switch: 'Switch',
  uploader: 'Uploader',
  dialog: 'Dialog',
  sheet: 'Sheet',
  'confirm-dialog': 'ConfirmDialog',
  menu: 'Menu',
  popover: 'Popover',
  tooltip: 'Tooltip',
  select: 'Select',
  sonner: 'Sonner',
  spinner: 'Spinner',
  'loading-overlay': 'LoadingOverlay',
  nprogress: 'NProgress',
  skeleton: 'Skeleton',
}

const DOCS = {
  sidebar: {
    title: 'Sidebar',
    description: 'Application sidebar layout with collapsible groups and navigation items.',
    originalDocs: null,
    examples: [],
  },
  accordion: {
    title: 'Accordion',
    description: 'Vertically stacked headers that reveal one or more sections of content.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/Accordion.html',
    examples: ['AccordionDemo'],
  },
  avatar: {
    title: 'Avatar',
    description: 'An image with a fallback for representing the user.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/Avatar.html',
    examples: ['AvatarDemo', 'AvatarSizes'],
  },
  separator: {
    title: 'Separator',
    description: 'Divides sections or menus (Reka Separator).',
    originalDocs: 'https://www.radix-ui.com/primitives/docs/components/separator',
    examples: ['SeparatorDemo'],
  },
  button: {
    title: 'Button',
    description: 'A button allows a user to perform an action, with mouse, touch, and keyboard interactions.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/Button.html',
    sourceCode: 'https://github.com/henry-phm/base-stack/blob/main/packages/ui/src/components/Button.tsx',
    examples: [
      'ButtonDemo',
      'ButtonOutline',
      'ButtonDestructive',
      'ButtonGhost',
      'ButtonIcon',
      'ButtonWithIcon',
      'ButtonAsALink',
      'ButtonLoading',
    ],
  },
  'file-trigger': {
    title: 'FileTrigger',
    description: 'Opens the file picker without a native file input.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/FileTrigger.html',
    examples: ['FileTriggerDemo', 'FileTriggerAvatar'],
  },
  table: {
    title: 'Table',
    description: 'Table layout for tabular data.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/Table.html',
    examples: ['TableDemo'],
  },
  'data-table': {
    title: 'DataTable',
    description: 'A component to render a data table.',
    originalDocs: 'https://tanstack.com/table/latest',
    sourceCode:
      'https://github.com/henry-phm/base-stack/blob/main/packages/ui/src/components/DataTable.tsx',
    examples: [
      'DataTableDemo',
      'DataTableColumnAlignment',
      'DataTableLoadingState',
      'DataTableRealworld',
      'DataTableRowSelection',
      'DataTableSorting',
      'DataTableSticky',
    ],
  },
  pagination: {
    title: 'Pagination',
    description:
      'Page controls (`Pagination`) and items-per-page select (`PaginationPageSizeSelector`), mirroring frontend-sample.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/Pagination.html',
    examples: ['PaginationDemo', 'PaginationWithPageSelector', 'PaginationWithQueryParams'],
  },
  'search-field': {
    title: 'Searchfield',
    description: 'Search text field with clear affordances.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/SearchField.html',
    examples: ['SearchfieldDemo'],
  },
  calendar: {
    title: 'Calendar',
    description: 'A calendar displays one or more date grids and allows users to select dates.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/Calendar.html',
    examples: ['CalendarControlled', 'CalendarDemo', 'CalendarMinMax', 'CalendarUnstyled'],
  },
  'range-calendar': {
    title: 'RangeCalendar',
    description: 'Calendar for selecting a contiguous range of dates.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/RangeCalendar.html',
    examples: [
      'RangeCalendarControlled',
      'RangeCalendarDefault',
      'RangeCalendarDemo',
      'RangeCalendarMinMax',
      'RangeCalendarUnstyled',
    ],
  },
  'date-picker': {
    title: 'DatePicker',
    description: 'A date picker combines a DateField and a Calendar popover.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/DatePicker.html',
    examples: ['DatePickerDemo', 'DatePickerDisabled', 'DatePickerForm', 'DatePickerWithLabel'],
  },
  'date-range-picker': {
    title: 'DateRangePicker',
    description: 'Picker for a contiguous date range.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/DateRangePicker.html',
    examples: [
      'DateRangePickerDemo',
      'DateRangePickerDisabled',
      'DateRangePickerForm',
      'DateRangePickerWithLabel',
    ],
  },
  'date-field': {
    title: 'DateField',
    description: 'Field for typing and editing a date with segments.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/DateField.html',
    examples: ['DateFieldDemo', 'DateFieldDisabled', 'DateFieldForm', 'DateFieldWithLabel'],
  },
  'time-field': {
    title: 'TimeField',
    description: 'Field for typing time of day with segments.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/TimeField.html',
    examples: [
      'TimeFieldDemo',
      'TimeFieldDisabled',
      'TimeFieldForm',
      'TimeFieldGranularity',
      'TimeFieldWithLabel',
    ],
  },
  input: {
    title: 'Input',
    description: 'Text input for forms.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/TextField.html',
    examples: ['InputDemo', 'InputDisabled', 'InputFile', 'InputForm', 'InputWithButton', 'InputWithLabel'],
  },
  textarea: {
    title: 'Textarea',
    description: 'Multi-line text input.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/TextField.html',
    examples: [
      'TextAreaDemo',
      'TextAreaDisabled',
      'TextAreaForm',
      'TextAreaWithButton',
      'TextAreaWithLabel',
    ],
  },
  'number-field': {
    title: 'NumberField',
    description: 'Numeric field with stepper and formatting options.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/NumberField.html',
    examples: [
      'NumberFieldCurrency',
      'NumberFieldDemo',
      'NumberFieldDisabled',
      'NumberFieldForm',
      'NumberFieldMinMax',
      'NumberFieldPercentages',
      'NumberFieldUnits',
      'NumberFieldWithLabel',
      'NumberFieldWithoutStepper',
    ],
  },
  checkbox: {
    title: 'Checkbox',
    description: 'Binary toggle with optional indeterminate state.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/Checkbox.html',
    examples: [
      'CheckboxDemo',
      'CheckboxDisabled',
      'CheckboxForm',
      'CheckboxGroupDemo',
      'CheckboxIndeterminate',
    ],
  },
  'radio-group': {
    title: 'RadioGroup',
    description: 'Pick one option from a set.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/RadioGroup.html',
    examples: ['RadioGroupDemo', 'RadioGroupDisabled', 'RadioGroupForm', 'RadioGroupHorizontal'],
  },
  switch: {
    title: 'Switch',
    description: 'On/off control.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/Switch.html',
    examples: ['SwitchDemo', 'SwitchDisabled', 'SwitchForm'],
  },
  uploader: {
    title: 'Uploader',
    description: 'File upload with progress, validation, and retries.',
    examples: [
      'UploaderCustom',
      'UploaderDemo',
      'UploaderDisabled',
      'UploaderForm',
      'UploaderRetry',
      'UploaderValidation',
      'UploaderVariantListType',
      'UploaderVariantTriggerType',
    ],
  },
  dialog: {
    title: 'Dialog',
    description: 'Modal dialog for focused tasks.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/Modal.html',
    examples: ['DialogDemo'],
  },
  sheet: {
    title: 'Sheet',
    description: 'Drawer / side panel overlay.',
    examples: ['SheetDemo'],
  },
  'confirm-dialog': {
    title: 'ConfirmDialog',
    description: 'Opinionated confirmation modal.',
    examples: ['ConfirmDialogDemo', 'ConfirmDialogDestructive'],
  },
  menu: {
    title: 'Menu',
    description: 'Dropdown menu with items, separators, and submenus.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/Menu.html',
    examples: ['MenuDemo', 'MenuWithIcon', 'MenuWithKeyboard', 'MenuWithSubmenu'],
  },
  popover: {
    title: 'Popover',
    description: 'Floating region anchored to a trigger.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/Popover.html',
    examples: ['PopoverBottomRight', 'PopoverDemo'],
  },
  tooltip: {
    title: 'Tooltip',
    description: 'Short hint on hover or focus.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/Tooltip.html',
    examples: ['TooltipBasic', 'TooltipCustom', 'TooltipDemo'],
  },
  select: {
    title: 'Select',
    description: 'Listbox-based selection control.',
    originalDocs: 'https://react-spectrum.adobe.com/react-aria/Select.html',
    examples: [
      'SelectDemo',
      'SelectCustom',
      'SelectDisabled',
      'SelectForm',
      'SelectMultiple',
      'SelectMultipleCustomization',
      'SelectMultipleDisabled',
      'SelectSearchable',
      'SelectMultipleSearchable',
      'SelectWithClearButton',
    ],
  },
  sonner: {
    title: 'Sonner',
    description: 'Toast notifications (vue-sonner).',
    examples: ['SonnerDemo'],
  },
  spinner: {
    title: 'Spinner',
    description: 'Inline loading indicator.',
    examples: ['SpinnerDemo'],
  },
  'loading-overlay': {
    title: 'LoadingOverlay',
    description: 'Full-surface loading state.',
    examples: ['LoadingOverlayDemo'],
  },
  nprogress: {
    title: 'NProgress',
    description: 'Top-of-page progress bar for navigation.',
    examples: ['NProgressDemo'],
  },
  skeleton: {
    title: 'Skeleton',
    description: 'Placeholder while content loads.',
    examples: ['SkeletonDemo', 'SkeletonScreen'],
  },
}

function toHeading(exampleName, prefix) {
  const rest = exampleName.startsWith(prefix) ? exampleName.slice(prefix.length) : exampleName
  const spaced = rest.replace(/([A-Z])/g, ' $1').trim()
  return spaced || 'Demo'
}

function buildMarkdown(slug, meta) {
  const lines = ['---']
  lines.push(`title: ${meta.title}`)
  lines.push(`description: ${meta.description}`)
  if (meta.originalDocs) lines.push(`originalDocs: ${meta.originalDocs}`)
  if (meta.sourceCode) lines.push(`sourceCode: ${meta.sourceCode}`)
  lines.push('---')
  lines.push('')

  if (!meta.examples?.length) {
    lines.push('## Overview')
    lines.push('')
    lines.push(
      'This layout is provided as a Vue component in `@/ui/components/sidebar`. Wire it in your app shell alongside navigation state.',
    )
    lines.push('')
    return lines.join('\n')
  }

  const prefix = PREFIX[slug]
  const [first, ...rest] = meta.examples
  lines.push('## Usage')
  lines.push('')
  lines.push(`<ComponentPreview name="${first}" />`)
  lines.push('')
  if (rest.length) {
    lines.push('---')
    lines.push('')
    lines.push('## Examples')
    lines.push('')
    for (const ex of rest) {
      lines.push(`### ${toHeading(ex, prefix)}`)
      lines.push('')
      lines.push(`<ComponentPreview name="${ex}" />`)
      lines.push('')
      lines.push('---')
      lines.push('')
    }
  }
  // trim trailing ---
  let out = lines.join('\n')
  out = out.replace(/\n---\n\n$/u, '\n')
  return out
}

fs.mkdirSync(contentDir, { recursive: true })

for (const [slug, meta] of Object.entries(DOCS)) {
  const outPath = path.join(contentDir, `${slug}.md`)
  fs.writeFileSync(outPath, buildMarkdown(slug, meta), 'utf8')
}

/** Esab-only pages (not in sample sidebar) */
const EXTRA = {
  field: {
    title: 'Field',
    description: 'Label and grouped input shell (FieldGroup) for form controls.',
    examples: ['FieldDemo'],
  },
}

for (const [slug, meta] of Object.entries(EXTRA)) {
  const lines = ['---', `title: ${meta.title}`, `description: ${meta.description}`, '---', '', '## Usage', '']
  for (const ex of meta.examples) {
    lines.push(`<ComponentPreview name="${ex}" />`)
    lines.push('')
  }
  fs.writeFileSync(path.join(contentDir, `${slug}.md`), lines.join('\n'), 'utf8')
}

console.log(`Wrote ${Object.keys(DOCS).length + Object.keys(EXTRA).length} ui docs under ${contentDir}`)
