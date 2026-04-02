import { createHighlighter, type Highlighter } from 'shiki'

let highlighterPromise: Promise<Highlighter> | null = null

function getHighlighter(): Promise<Highlighter> {
  if (!highlighterPromise) {
    highlighterPromise = createHighlighter({
      themes: ['github-light', 'github-dark'],
      langs: ['vue', 'typescript', 'javascript', 'css', 'html', 'json'],
    })
  }
  return highlighterPromise
}

export async function highlightVueSource(code: string, dark: boolean): Promise<string> {
  const highlighter = await getHighlighter()
  return highlighter.codeToHtml(code, {
    lang: 'vue',
    theme: dark ? 'github-dark' : 'github-light',
  })
}
