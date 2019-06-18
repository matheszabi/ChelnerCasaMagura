package ro.matheszabi.casamagura

import java.util.*

class MenuLoader {

    fun loadMenu(content: String): TreeNode<String> {
        val tk = StringTokenizer(content, "\r\n", false)

        val deepMenu = TreeNode<String>("Root")
        var tabCount = -1;
        var node = deepMenu

        while (tk.hasMoreTokens()){
            var curLine = tk.nextToken()

            if (curLine != null) {
                // if there is not a white line
                if (curLine.trim().length > 0) {
                    //count the tabs, if there are: that's the menu level
                    var curTabCount = countBeginningTabs(curLine)

                    when {
                        (curTabCount == 0) -> {// can be break from 2 level
                            var curNode = deepMenu.addChild(curLine.trim())
                            node = curNode
                            tabCount = 0
                        }
                        (tabCount < curTabCount) -> {// add child:
                            var curNode = node.addChild(curLine.trim())
                            node = curNode
                            tabCount = curTabCount
                        }
                        (tabCount == curTabCount) ->{ // add sibling
                            var curNode = node.parent.addChild(curLine.trim())
                            node = curNode
                        }
                        (tabCount > curTabCount) ->{// the last was a leaf, now add a sibling to the parent
                            var curNode = node.parent.parent.addChild(curLine.trim())
                            node = curNode
                            tabCount = curTabCount
                        }
                    }
                }
            }
        }

        return deepMenu;
    }

    private fun countBeginningTabs(line: String): Int {
        var tabsCount = 0
        var chars = line.toCharArray()
        for (i in chars.indices) {
            if (chars[i] == '\t') {
                tabsCount++
            } else {
                break
            }
        }
        return tabsCount
    }
}