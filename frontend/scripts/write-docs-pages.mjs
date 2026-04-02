import fs from 'node:fs'
import path from 'node:path'

const root = path.resolve(import.meta.dirname, '..')
const outDir = path.join(root, 'src/views/docs/pages')

const pages = [
  ['accordion', 'Accordion', [['Usage', 'AccordionDemo', 'max-w-[450px] mx-auto']]],
  [
    'avatar',
    'Avatar',
    [
      ['Demo', 'AvatarDemo', ''],
      ['Sizes', 'AvatarSizes', ''],
    ],
  ],
  [
    'button',
    'Button',
    [
      ['Usage', 'ButtonDemo', ''],
      ['Outline', 'ButtonOutline', ''],
      ['Destructive', 'ButtonDestructive', ''],
      ['Ghost', 'ButtonGhost', ''],
      ['Icon', 'ButtonIcon', ''],
      ['With icon', 'ButtonWithIcon', ''],
      ['As a link', 'ButtonAsALink', ''],
      ['Loading', 'ButtonLoading', ''],
    ],
  ],
  [
    'calendar',
    'Calendar',
    [
      ['Usage', 'CalendarDemo', 'max-w-[360px] mx-auto'],
      ['Controlled', 'CalendarControlled', 'max-w-[360px] mx-auto'],
      ['Unstyled', 'CalendarUnstyled', 'max-w-[360px] mx-auto'],
      ['Min / max', 'CalendarMinMax', 'max-w-[360px] mx-auto'],
    ],
  ],
  [
    'checkbox',
    'Checkbox',
    [
      ['Usage', 'CheckboxDemo', ''],
      ['Disabled', 'CheckboxDisabled', ''],
      ['Indeterminate', 'CheckboxIndeterminate', ''],
      ['Group', 'CheckboxGroupDemo', ''],
      ['Form', 'CheckboxForm', ''],
    ],
  ],
  ['combobox', 'Combobox', [['Usage', 'ComboBoxDemo', 'max-w-[320px] mx-auto']]],
  [
    'confirm-dialog',
    'Confirm dialog',
    [
      ['Usage', 'ConfirmDialogDemo', ''],
      ['Destructive', 'ConfirmDialogDestructive', ''],
    ],
  ],
  [
    'data-table',
    'Data table',
    [
      ['Usage', 'DataTableDemo', ''],
      ['Column alignment', 'DataTableColumnAlignment', ''],
      ['Loading', 'DataTableLoadingState', ''],
      ['Row selection', 'DataTableRowSelection', ''],
      ['Sorting', 'DataTableSorting', ''],
      ['Sticky columns', 'DataTableSticky', ''],
      ['Kitchen sink', 'DataTableRealworld', ''],
    ],
  ],
  [
    'date-field',
    'Date field',
    [
      ['Usage', 'DateFieldDemo', 'max-w-[280px] mx-auto'],
      ['Disabled', 'DateFieldDisabled', 'max-w-[280px] mx-auto'],
      ['With label', 'DateFieldWithLabel', 'max-w-[280px] mx-auto'],
      ['Form', 'DateFieldForm', 'max-w-[280px] mx-auto'],
    ],
  ],
  [
    'date-picker',
    'Date picker',
    [
      ['Usage', 'DatePickerDemo', 'max-w-[400px] mx-auto'],
      ['Disabled', 'DatePickerDisabled', 'max-w-[400px] mx-auto'],
      ['With label', 'DatePickerWithLabel', 'max-w-[400px] mx-auto'],
      ['Form', 'DatePickerForm', 'max-w-[400px] mx-auto'],
    ],
  ],
  [
    'date-range-picker',
    'Date range picker',
    [
      ['Usage', 'DateRangePickerDemo', 'max-w-[400px] mx-auto'],
      ['Disabled', 'DateRangePickerDisabled', 'max-w-[400px] mx-auto'],
      ['With label', 'DateRangePickerWithLabel', 'max-w-[400px] mx-auto'],
      ['Form', 'DateRangePickerForm', 'max-w-[400px] mx-auto'],
    ],
  ],
  ['dialog', 'Dialog', [['Usage', 'DialogDemo', '']]],
  [
    'file-trigger',
    'File trigger',
    [
      ['Usage', 'FileTriggerDemo', ''],
      ['Avatar pick', 'FileTriggerAvatar', ''],
    ],
  ],
  [
    'input',
    'Input',
    [
      ['Usage', 'InputDemo', 'max-w-[300px] mx-auto'],
      ['File', 'InputFile', 'max-w-[300px] mx-auto'],
      ['Disabled', 'InputDisabled', 'max-w-[300px] mx-auto'],
      ['With label', 'InputWithLabel', 'max-w-[300px] mx-auto'],
      ['With button', 'InputWithButton', 'max-w-[400px] mx-auto'],
      ['Form', 'InputForm', 'max-w-[400px] mx-auto'],
    ],
  ],
  ['loading-overlay', 'Loading overlay', [['Usage', 'LoadingOverlayDemo', '']]],
  [
    'menu',
    'Menu',
    [
      ['Usage', 'MenuDemo', ''],
      ['With icons', 'MenuWithIcon', ''],
      ['Submenu', 'MenuWithSubmenu', ''],
      ['Shortcuts', 'MenuWithKeyboard', ''],
    ],
  ],
  ['nprogress', 'NProgress', [['Usage', 'NProgressDemo', '']]],
  [
    'number-field',
    'Number field',
    [
      ['Usage', 'NumberFieldDemo', 'max-w-[280px] mx-auto'],
      ['Without stepper', 'NumberFieldWithoutStepper', 'max-w-[280px] mx-auto'],
      ['Currency', 'NumberFieldCurrency', 'max-w-[280px] mx-auto'],
      ['Percent', 'NumberFieldPercentages', 'max-w-[280px] mx-auto'],
      ['Units', 'NumberFieldUnits', 'max-w-[280px] mx-auto'],
      ['Min / max', 'NumberFieldMinMax', 'max-w-[280px] mx-auto'],
      ['Disabled', 'NumberFieldDisabled', 'max-w-[280px] mx-auto'],
      ['With label', 'NumberFieldWithLabel', 'max-w-[280px] mx-auto'],
      ['Form', 'NumberFieldForm', 'max-w-[280px] mx-auto'],
    ],
  ],
  [
    'pagination',
    'Pagination',
    [
      ['Usage', 'PaginationDemo', ''],
      ['Page size', 'PaginationWithPageSelector', ''],
      ['Query param', 'PaginationWithQueryParams', ''],
    ],
  ],
  [
    'popover',
    'Popover',
    [
      ['Usage', 'PopoverDemo', ''],
      ['Bottom end', 'PopoverBottomRight', ''],
    ],
  ],
  [
    'radio-group',
    'Radio group',
    [
      ['Usage', 'RadioGroupDemo', ''],
      ['Horizontal', 'RadioGroupHorizontal', ''],
      ['Disabled', 'RadioGroupDisabled', ''],
      ['Form', 'RadioGroupForm', 'max-w-[300px] mx-auto'],
    ],
  ],
  [
    'range-calendar',
    'Range calendar',
    [
      ['Usage', 'RangeCalendarDemo', 'max-w-[340px] mx-auto'],
      ['Default', 'RangeCalendarDefault', 'max-w-[340px] mx-auto'],
      ['Controlled', 'RangeCalendarControlled', 'max-w-[340px] mx-auto'],
      ['Unstyled', 'RangeCalendarUnstyled', 'max-w-[340px] mx-auto'],
      ['Min / max', 'RangeCalendarMinMax', 'max-w-[340px] mx-auto'],
    ],
  ],
  ['search-field', 'Search field', [['Usage', 'SearchfieldDemo', 'max-w-[300px] mx-auto']]],
  [
    'select',
    'Select',
    [
      ['Usage', 'SelectDemo', 'max-w-[300px] mx-auto'],
      ['Disabled', 'SelectDisabled', 'max-w-[300px] mx-auto'],
      ['Searchable', 'SelectSearchable', 'max-w-[300px] mx-auto'],
      ['Groups', 'SelectCustom', 'max-w-[300px] mx-auto'],
      ['Multiple', 'SelectMultiple', 'max-w-[350px] mx-auto'],
      ['Multiple disabled', 'SelectMultipleDisabled', 'max-w-[350px] mx-auto'],
      ['Multiple searchable', 'SelectMultipleSearchable', 'max-w-[350px] mx-auto'],
      ['Styled options', 'SelectMultipleCustomization', 'max-w-[350px] mx-auto'],
      ['With clear', 'SelectWithClearButton', 'max-w-[350px] mx-auto'],
      ['Form', 'SelectForm', 'max-w-[350px] mx-auto'],
    ],
  ],
  ['separator', 'Separator', [['Usage', 'SeparatorDemo', 'max-w-[300px] mx-auto']]],
  ['sheet', 'Sheet', [['Usage', 'SheetDemo', '']]],
  [
    'sidebar',
    'Sidebar',
    [
      [
        '_html',
        '_html',
        `The collapsible sidebar and dashboard shell live in <code>layouts/DashboardLayout.vue</code> and <code>components/ui/sidebar/*</code>.`,
      ],
    ],
  ],
  [
    'skeleton',
    'Skeleton',
    [
      ['Usage', 'SkeletonDemo', ''],
      ['Screen', 'SkeletonScreen', 'max-w-[600px] mt-4 mx-auto'],
    ],
  ],
  ['sonner', 'Sonner', [['Usage', 'SonnerDemo', '']]],
  ['spinner', 'Spinner', [['Usage', 'SpinnerDemo', '']]],
  [
    'switch',
    'Switch',
    [
      ['Usage', 'SwitchDemo', 'max-w-[300px] mx-auto'],
      ['Disabled', 'SwitchDisabled', 'max-w-[300px] mx-auto'],
      ['Form', 'SwitchForm', 'max-w-[300px] mx-auto'],
    ],
  ],
  ['table', 'Table', [['Usage', 'TableDemo', '']]],
  [
    'textarea',
    'Textarea',
    [
      ['Usage', 'TextAreaDemo', 'max-w-[400px] mx-auto'],
      ['Disabled', 'TextAreaDisabled', 'max-w-[400px] mx-auto'],
      ['With label', 'TextAreaWithLabel', 'max-w-[400px] mx-auto'],
      ['With button', 'TextAreaWithButton', 'max-w-[400px] mx-auto'],
      ['Form', 'TextAreaForm', 'max-w-[400px] mx-auto'],
    ],
  ],
  [
    'time-field',
    'Time field',
    [
      ['Usage', 'TimeFieldDemo', 'max-w-[200px] mx-auto'],
      ['Disabled', 'TimeFieldDisabled', 'max-w-[200px] mx-auto'],
      ['Granularity', 'TimeFieldGranularity', 'max-w-[300px] mx-auto'],
      ['With label', 'TimeFieldWithLabel', 'max-w-[200px] mx-auto'],
      ['Form', 'TimeFieldForm', 'max-w-[300px] mx-auto'],
    ],
  ],
  [
    'tooltip',
    'Tooltip',
    [
      ['Usage', 'TooltipDemo', ''],
      ['Basic', 'TooltipBasic', ''],
      ['Custom', 'TooltipCustom', ''],
    ],
  ],
  [
    'uploader',
    'Uploader',
    [
      ['Usage', 'UploaderDemo', 'max-w-[500px] mx-auto'],
      ['Custom', 'UploaderCustom', 'max-w-[500px] mx-auto'],
      ['Retry', 'UploaderRetry', 'max-w-[500px] mx-auto'],
      ['Disabled', 'UploaderDisabled', 'max-w-[500px] mx-auto'],
      ['List layout', 'UploaderVariantListType', 'max-w-[500px] mx-auto'],
      ['Trigger layout', 'UploaderVariantTriggerType', 'max-w-[500px] mx-auto'],
      ['Validation', 'UploaderValidation', 'max-w-[500px] mx-auto'],
      ['Form', 'UploaderForm', 'max-w-[500px] mx-auto'],
    ],
  ],
]

fs.mkdirSync(outDir, { recursive: true })

for (const [slug, title, sections] of pages) {
  const compImports = new Set()
  for (const [h, comp] of sections) {
    if (h !== '_html' && comp !== '_html') compImports.add(comp)
  }
  const lines = []
  const needsPreview = sections.some(([h]) => h !== '_html')
  if (needsPreview) {
    lines.push("import DocComponentPreview from '@/components/docs/DocComponentPreview.vue'")
  }
  if (slug === 'sidebar') {
    lines.push(`import { RouterLink } from 'vue-router'`)
  }
  for (const c of [...compImports].sort()) {
    lines.push(`import ${c} from '@/docs/examples/${c}.vue'`)
  }

  const body = []
  for (let i = 0; i < sections.length; i++) {
    const [heading, comp, cclass] = sections[i]
    if (heading === '_html') {
      body.push(`    <p class="text-muted-foreground">${cclass}</p>`)
      body.push(
        `    <div class="not-prose mt-4 rounded-lg border bg-card p-4">\n      <RouterLink to="/dashboard" class="text-primary underline-offset-4 hover:underline">Open dashboard layout</RouterLink>\n    </div>`,
      )
      continue
    }
    const hx = i === 0 ? 'h2' : 'h3'
    const attrs = [`example="${comp}"`]
    if (cclass) attrs.push(`container-class="${cclass}"`)
    body.push(`    <${hx}>${heading}</${hx}>`)
    body.push(`    <DocComponentPreview ${attrs.join(' ')}>`)
    body.push(`      <${comp} />`)
    body.push(`    </DocComponentPreview>`)
  }

  const intro =
    slug === 'button'
      ? `<p> A button triggers an action. Use <code>UiButton</code> or <code>buttonVariants</code> on links. </p>`
      : `<p> Live examples mirroring the sample docs. Implementation: <code>src/components/ui</code>. </p>`

  const file = `<script setup lang="ts">\n${lines.join('\n')}\n</script>\n\n<template>\n  <article class="prose dark:prose-invert max-w-none">\n    <h1 class="text-foreground">${title}</h1>\n    ${intro}\n${body.join('\n')}\n  </article>\n</template>\n`

  fs.writeFileSync(path.join(outDir, `${slug}.vue`), file)
}
