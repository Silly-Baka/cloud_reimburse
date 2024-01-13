import { HtmlNode, HtmlNodeModel } from "@logicflow/core";

/**
 * 此类为自定义化节点类 -- 用于流程节点展示
 */

class ProcessNodeModel extends HtmlNodeModel {
  setAttributes() {
    this.text.editable = false; // 禁止节点文本编辑
    this.width = 145;
    this.height = 100;
  }
}

class ProcessNodeView extends HtmlNode {
  setHtml(rootEl) {
    const { properties } = this.props.model; // 映射节点定义上的properties属性

    let imgSrc = "";
    if (properties.type === "报销发起") {
      imgSrc = "apply.png";
    } else if (properties.type === "报销完成") {
      imgSrc = "end.png";
    } else {
      imgSrc = "waiting.png";
    }

    // 节点主视图
    const mainDiv = document.createElement("div");
    mainDiv.className = "node-container";
    mainDiv.classList.add(`state-${properties.state}`);
    const html = `
    <div class="left-area">
      <img class="icon" src="${imgSrc}">
    </div>
    <div class="right-area">
     <div class="info-content">
       <span class="node-type">${properties.type}</span>
        <span class="node-oprUser">${properties.oprUser}</span>
     </div>
    </div>
    `;
    mainDiv.innerHTML = html;

    // 弹出框
    const popperContent = document.createElement("div");
    const popperHtml = `
    <div class="isHover-${properties.isHover} hover-content">
      反馈：${properties.feedback}
    </div>
    `;
    popperContent.innerHTML = popperHtml;

    rootEl.innerHTML = "";
    rootEl.appendChild(mainDiv);
    rootEl.appendChild(popperContent);
  }
}

export default {
  type: "process-node",
  view: ProcessNodeView,
  model: ProcessNodeModel,
};
