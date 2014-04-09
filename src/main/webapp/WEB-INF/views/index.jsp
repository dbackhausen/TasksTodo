<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en" manifest="/cache.manifest">
  <head>
    <title>TasksTodo</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <link rel="stylesheet" href="<c:url value="/resources/css/normalize.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-theme.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/jquery.fileupload.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-tagsinput.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/datepicker3.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
    <script src="<c:url value="/resources/js/jquery-1.10.2.min.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery.ui.widget.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery.iframe-transport.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery.fileupload.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery-ui-1.10.4.custom.min.js"/>"></script>
    <script src="<c:url value="/resources/js/respond.min.js"/>"></script>
    <script src="<c:url value="/resources/js/modernizr-2.6.2.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap-paginator.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap-tagsinput.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap-datepicker.js"/>"></script>
    <script src="<c:url value="/resources/js/typeahead.min.js"/>"></script>
    <script src="<c:url value="/resources/js/knockout-3.0.0.js"/>"></script>
    <script src="<c:url value="/resources/js/knockout.punches.min.js"/>"></script>
    <script src="<c:url value="/resources/js/moment.min.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery.nestable.js"/>"></script>
    <script src="<c:url value="/resources/js/tasks.js"/>"></script>
  </head>
  <body>
    <nav class="navbar navbar-default" role="navigation">
      <div class="navbar-header">
        <a class="navbar-brand" href="">Tasks Todo</a>
      <p class="navbar-text navbar-right">Signed in as Daniel</p>
      </div>
    </nav>
  
    <div id="tasks" class="row">
      <div id="tasks-list" class="col-md-3">
        <h1 class="goal-header">Inbox</h1>
        <div id="nestable-task-list" class="dd">
          <ol data-bind="foreach: tasks" class="task-list dd-list">
            <li class="dd-item dd3-item" data-bind="attr: { id: idAsString }">
              <div class="dd-handle dd3-handle"></div>
              <div data-bind="click: $parent.selectTask, css: { selected: ($root.selectedTask() != null && $root.selectedTask().idAsString == idAsString) }" class="dd3-content"><span data-bind="text: title"></span></div>
            </li>
          </ol>
        </div>
        <hr/>
        <button id="new-task-button" data-bind="click: newTask" class="btn btn-primary btn-sm"><spring:message code="button.task.add" /></button>
        <form id="new-task-form" data-bind="submit: addTask">
          <div class="form-group">
            <input id="input-new-task-title" class="form-control">
          </div>
          <button type="submit" class="btn btn-primary btn-sm"><spring:message code="button.task.add" /></button>
          <button data-bind="click: cancelNewTask" class="btn btn-default btn-sm"><spring:message code="button.cancel" /></button>
        </form>
      </div>

      <div class="col-md-9" data-bind="if: selectedTask() != null">
        <!-- Navigation tabs -->
        <ul id="task-nav-tab" class="nav nav-tabs">
          <li id="tab-details" class="active"><a href="#details" data-toggle="tab"><i class="glyphicon glyphicon-th-large"></i><span><spring:message code="tab.task.details" /></span></a></li>
          <li id="tab-notes"><a href="#notes" data-toggle="tab"><i class="glyphicon glyphicon-comment"></i><span><spring:message code="tab.task.notes" /></span></a></li>
          <li id="tab-links"><a href="#links" data-toggle="tab"><i class="glyphicon glyphicon-link"></i><span><spring:message code="tab.task.links" /></span></a></li>
          <li id="tab-files"><a href="#files" data-toggle="tab"><i class="glyphicon glyphicon-file"></i><span><spring:message code="tab.task.files" /></span></a></li>
          <li id="tab-history"><a href="#history" data-toggle="tab"><i class="glyphicon glyphicon-hdd"></i><span><spring:message code="tab.task.history" /></span></a></li>
        </ul>
          
        <!-- Tab panes -->
        <div class="tab-content">
          <!-- 
            TAB: DETAILS
            -->
          <div id="details" class="tab-pane fade in active">
            <div class="inline-view">
              <p><label for="task-details-title">Title:</label>
              <span id="task-details-title" data-bind="text: selectedTask().title"></span></p>
              <p><label for="task-details-title">Description:</label>
              <span data-bind="text: selectedTask().description"></span></p>
              <p><label for="task-details-title">Create date:</label>
              <span data-bind="text: selectedTask().created | smartdate"></span></p>
              <p><label for="task-details-title">Modified date:</label>
              <span data-bind="text: selectedTask().modified | smartdate"></span></p>
              <p><label for="task-details-title">Due date:</label>
              <span data-bind="text: selectedTask().dueDate | smartdate"></span></p>
              <p><label for="task-details-title">Completed date:</label>
              <span data-bind="text: selectedTask().completedDate | smartdate"></span></p>
              <p><label for="task-details-title">Reminder date:</label>
              <span data-bind="text: selectedTask().reminderDate | smartdate"></span></p>
              <p><label for="task-details-title">Urgency:</label>
              <span data-bind="text: selectedTask().urgency | smarturgency"></span></p>
              <p><label for="task-details-title">Priority:</label>
              <span data-bind="text: selectedTask().priority | smartpriority"></span></p>
              <hr />
              <div class="item-container">
                <div class="item-modified-date">
                  Created: <span data-bind="text: selectedTask().created | smartdate"></span> |
                  Modified: <span data-bind="text: selectedTask().modified | smartdate"></span>
                </div>
                <div class="item-edit-link">
                  <a href="#" data-bind="click: editTask">edit</a>
                </div>
                <div class="item-delete-link">
                  <a href="#" data-bind="click: deleteTask">delete</a>
                </div>
              </div>
            </div>
            <div class="inline-edit">
              <p><label for="task-details-title">Title</label>
              <input type="text" data-bind="value: selectedTask().title" class="form-control" /></p>
              <p><label for="task-details-title">Description</label>
              <textarea data-bind="value: selectedTask().description" class="form-control"></textarea></p>
              <p><label for="task-details-title">Due date</label>
              <input type="text" data-bind="value: selectedTask().dueDate" class="form-control" data-provide="datepicker" data-date-format="yyyy-mm-dd" /></p>
              <p><label for="task-details-title">Reminder date</label>
              <input type="text" data-bind="value: selectedTask().reminderDate" class="form-control" data-provide="datepicker" data-date-format="yyyy-mm-dd" /></p>
<!--              <p><label for="task-details-title">Urgency</label>
               <div class="btn-group" data-toggle="buttons">
                <label class="btn btn-primary">
                  <input type="radio" name="urgency" value="1" data-bind="checked: selectedTask().urgency"> low
                </label>
                <label class="btn btn-primary">
                  <input type="radio" name="urgency" value="2" data-bind="checked: selectedTask().urgency"> mid
                </label>
                <label class="btn btn-primary">
                  <input type="radio" name="urgency" value="3" data-bind="checked: selectedTask().urgency"> high
                </label>
              </div>
              <p><label for="task-details-title">Priority</label>
              <div class="btn-group" data-toggle="buttons">
                <label class="btn btn-primary">
                  <input type="radio" name="priority" value="1" data-bind="checked: selectedTask().priority"> low
                </label>
                <label class="btn btn-primary">
                  <input type="radio" name="priority" value="2" data-bind="checked: selectedTask().priority"> mid
                </label>
                <label class="btn btn-primary">
                  <input type="radio" name="priority" value="3" data-bind="checked: selectedTask().priority"> high
                </label>
              </div> -->
              <p><label for="task-details-tags">Tags</label>
              <input id="task-details-tags" type="text" value="" class="form-control task-tags" /></p>
              <hr/>
              <button data-bind="click: saveTask" class="btn btn-primary btn-sm"><spring:message code="button.save" /></button>
              <button data-bind="click: cancelEditTask" class="btn btn-default btn-sm"><spring:message code="button.cancel" /></button>
            </div>
          </div>

          <!-- 
            TAB: NOTES
            -->
          <div id="notes" class="tab-pane fade">
            <button id="new-note-button" data-bind="click: newNote" class="btn btn-primary btn-sm"><spring:message code="button.note.add" /></button>
            <form id="new-note-form" data-bind="submit: addNote">
              <div class="form-group">
                <textarea id="input-new-note-body" class="form-control"></textarea>
              </div>
              <button type="submit" class="btn btn-primary btn-sm"><spring:message code="button.save" /></button>
              <button data-bind="click: cancelNewNote" class="btn btn-default btn-sm"><spring:message code="button.cancel" /></button>
            </form>
            <hr />
            <ul id="task-notes-list" data-bind="foreach: notes">
              <li data-bind="attr: { id: idAsString }">
                <div class="well well-sm inline-view">
                  <span data-bind="text: body"></span>
                  <hr/>
                  <div class="item-container">
                    <div class="item-modified-date">
                      <span data-bind="text: created | smartdate"></span>
                    </div>
                    <div class="item-edit-link">
                      <a href="#" data-bind="click: $parent.editNote">edit</a>
                    </div>
                    <div class="item-delete-link">
                      <a href="#" data-bind="click: $parent.deleteNote">delete</a>
                    </div>
                  </div>
                </div>
                <div class="inline-edit">
                  <div class="form-group">
                    <textarea data-bind="value: body" class="form-control"></textarea>
                  </div>
                  <button data-bind="click: $parent.saveNote" class="btn btn-primary btn-sm"><spring:message code="button.save" /></button>
                  <button data-bind="click: $parent.cancelEditNote" class="btn btn-default btn-sm"><spring:message code="button.cancel" /></button>
                </div>
              </li>
            </ul>
            <div id="notes-paginator"></div>
          </div>
          
          <!-- 
            TAB: LINKS
            -->
          <div id="links" class="tab-pane fade">
            <button id="new-link-button" data-bind="click: newLink" class="btn btn-primary btn-sm"><spring:message code="button.link.add" /></button>
            <form id="new-link-form" data-bind="submit: addLink">
              <div class="row">
                <div class="form-group col-md-6">
                  <label for="input-new-link-title">URL</label>
                  <input type="text" id="input-new-link-url" class="form-control" placeholder="The URL (e.g. http://www.taskstodo.org)"/>
                </div>
                <div class="form-group col-md-6">
                  <label for="input-new-link-title">Title</label>
                  <input type="text" id="input-new-link-title" class="form-control" placeholder="Tasks Todo Website"/>
                </div>
              </div>
              <div class="form-group">
                <label for="input-new-link-description">Description</label>
                <textarea id="input-new-link-description" class="form-control" placeholder="Some optional description"></textarea>
              </div>
              <button type="submit" class="btn btn-primary btn-sm"><spring:message code="button.save" /></button>
              <button data-bind="click: cancelNewLink" class="btn btn-default btn-sm"><spring:message code="button.cancel" /></button>
            </form>
            <hr />
            <ul data-bind="foreach: links" class="media-list">
              <li data-bind="attr: { id: idAsString }" class="media">
                <a class="pull-left" href="#">
                  <img class="media-object" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAFQElEQVR4Ae1aa1PbRhQ9smT5TbB5QwAXCDAtnQBDO+1k+v+/tZRHwIBxbNmyjV/gt6xX74owBFI3RNQfNN5lNJZW0tG9555zdz8g1Ot1G2M8fGOcu5M6J4ArYMwZ4BYYcwGAK4ArYMwZ4BYYcwHwJsgtwC0w5gxwC4y5APgqwC3ALTDmDHALjLkA+CrALfA6Cwjw+XywbRsWHexcEJ4iCjTxcDy9M+SKnvf5BMK0HFyG+Xzc4+Grbz1/7iXX0kse+vdnKDGYULNZKMUbmLaAxMwCflh9C1liCVCAFHy7XkYmp8Lnj2Bjcw2y+HVCD/gsMcvQkMlmULqpQxD9mF1YxsrbeYiw6Q8OOdWignyphmAkgY2N5Vf5eHg0D1EN+WWFKefSOD49R6unQe93cHl2jLO0wjK/L4/Rx9VFCkpBhVqqwrTup1miD2p5PGeEWshefMRZ6hp9w0Kv1cDp0SEyatUhkxGqd5tIpVIoqCrKlTqR8kxyQ+IdNu1SAVRhU0O+UIQtR7Cze4CY0EdWUSHJopOoXxSgfsrg5raHgEyfoeAFIkZr35EiCrBFGSvJNVjtChSqphSIYWVpEgUiSo5OYf9gD3ariny5CsnHLEYQAhF0fYmWZkP2iw7esMReOu+OAKqgqfXQG+iQpCBulDQUTcfE1CxWV5YoYKDfbuCS1JAgCYudG9T6lAH5IhAKoU+VzVeb6GsG7G4Vaq2Ndzv7sAddaCQTf1BwlDAwbCTmF7EyPwOR+sJdRUWmUMPSchKtchb6S7P8j+dcWYDyh04JW5YFg6RfqTbQajZwdX6C04tPTgPLpi+giWFsvVuDjzUEkqpASQj+ELZ+3MabsIxKSUHlrouZxSQ2kosY9HqOz/utW9Rum7itV3B+/DeulLLTG64u0/BPzGJtdQk2SYLZR2RefMVw9TbLh3VqKin84Ukc/PYBv+7vIOz3oV6ro1Epk5TrCAaDqJbyaA9MskwPOaWAgW4iOjlDcp+FRdVmjS65noRMkTCPU2YIx+fx+4c/sPvTOiTBRKXSQI1wKrcdhIMSimoeA+oner+JXL78qj7gigCWuBQIEPvkQ1GiRAOIkLQl1uGJHcs0IUoS+h1SxdU1ujpFa2lQsgWQqqmRtaiBNZyOaJk6VOolbJ7Zg1WVYQfpiITvrxmmaZhkNwnNagnXmRx0WnUMrQ1FKVLrdD/c9QAKSKTmNx2fQJo69MnxEfxmF+2+jvhCBBMz89iLTlDcpBLbwNnhn7gzwtjd20FItGi1OEeNpB+fniUybp3VREkksDobRzwawk1ZwdGJjUGzBp1Wg7k3UUwtLuCX+JxDkKF1cPTXIezoNN6/33KWSLcUuCPAEb+I5MYm2r0TknmBZgTEEnOO52VJhD8ac+Zg6QgFZfT0IKLRCO0LSihW7xCOTWJ752d0SmmkMiryuSzmZ3axub0F7eQcRSXnvD+1sIr1lUVSlIhYLHZPgF8ghcgwA2GEQ0HHNvSwqyG85n+EmFwtQ0en23U2QpFo1NnosF3hl4NZgq3XIlnEpsZp0sGWRImSerhmu0lmG7Y/MDQNXdYQBdEhja0A7P7jIEuwTcVnzMf57z97FQH3n6POTkQIAu3UqDN/Geb3h/P5DQePMCnp52S6xhzyomsLPOJR0hTokwI93nR35uD9T2R+IwKXq8A3UD10mxPgoWKNJFSugJHQ6iFQrgAPFWskoXIFjIRWD4FyBXioWCMJlStgJLR6CJQrwEPFGkmoXAEjodVDoFwBHirWSELlChgJrR4C5QrwULFGEipXwEho9RDoPwJqK/yQ8YYSAAAAAElFTkSuQmCC" alt="...">
                </a>
                <div class="inline-view">
                  <div class="media-body">
                    <h4 class="media-heading"><a data-bind="attr: { href: url }" target="_blank"><span data-bind="text: title"></span></a></h4>
                    <p><span data-bind="text: description" class="inline-view"></span></p>
                    <div class="item-container">
                      <div class="item-modified-date">
                        <span data-bind="text: created | smartdate"></span>
                      </div>
                      <div class="item-edit-link">
                        <a href="#" data-bind="click: $parent.editLink">edit</a>
                      </div>
                      <div class="item-delete-link">
                        <a href="#" data-bind="click: $parent.deleteLink">delete</a>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="inline-edit">
                  <div class="row">
                    <div class="form-group col-md-6">
                      <input data-bind="value: url" class="form-control"/>
                    </div>
                    <div class="form-group col-md-6">
                      <input data-bind="value: title" class="form-control"/>
                    </div>
                  </div>
                  <div class="form-group">
                    <textarea data-bind="value: description" class="form-control"></textarea>
                  </div>
                  <button data-bind="click: $parent.saveLink" class="btn btn-primary btn-sm"><spring:message code="button.save" /></button>
                  <button data-bind="click: $parent.cancelEditLink" class="btn btn-default btn-sm"><spring:message code="button.cancel" /></button>
                </div>
              </li>
            </ul>
          </div>
          
          <!-- 
            TAB: FILES
            -->
          <div id="files" class="tab-pane fade">
            <form id="task-file-form" data-bind="submit: addFile" method="post" enctype="multipart/form-data">     
              <span class="btn btn-success btn-sm fileinput-button">
                <i class="glyphicon glyphicon-plus"></i>
                <span>Add files...</span>
                <input type="file" name="files[]" multiple>
              </span>
              <button type="submit" class="btn btn-primary btn-sm"><spring:message code="button.save" /></button>
            </form>
            <hr />
            <ul data-bind="foreach: files" class="media-list">
              <li data-bind="attr: { id: idAsString }" class="media">
                <a class="pull-left" href="#">
                  <img class="media-object" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAFQElEQVR4Ae1aa1PbRhQ9smT5TbB5QwAXCDAtnQBDO+1k+v+/tZRHwIBxbNmyjV/gt6xX74owBFI3RNQfNN5lNJZW0tG9555zdz8g1Ot1G2M8fGOcu5M6J4ArYMwZ4BYYcwGAK4ArYMwZ4BYYcwHwJsgtwC0w5gxwC4y5APgqwC3ALTDmDHALjLkA+CrALfA6Cwjw+XywbRsWHexcEJ4iCjTxcDy9M+SKnvf5BMK0HFyG+Xzc4+Grbz1/7iXX0kse+vdnKDGYULNZKMUbmLaAxMwCflh9C1liCVCAFHy7XkYmp8Lnj2Bjcw2y+HVCD/gsMcvQkMlmULqpQxD9mF1YxsrbeYiw6Q8OOdWignyphmAkgY2N5Vf5eHg0D1EN+WWFKefSOD49R6unQe93cHl2jLO0wjK/L4/Rx9VFCkpBhVqqwrTup1miD2p5PGeEWshefMRZ6hp9w0Kv1cDp0SEyatUhkxGqd5tIpVIoqCrKlTqR8kxyQ+IdNu1SAVRhU0O+UIQtR7Cze4CY0EdWUSHJopOoXxSgfsrg5raHgEyfoeAFIkZr35EiCrBFGSvJNVjtChSqphSIYWVpEgUiSo5OYf9gD3ariny5CsnHLEYQAhF0fYmWZkP2iw7esMReOu+OAKqgqfXQG+iQpCBulDQUTcfE1CxWV5YoYKDfbuCS1JAgCYudG9T6lAH5IhAKoU+VzVeb6GsG7G4Vaq2Ndzv7sAddaCQTf1BwlDAwbCTmF7EyPwOR+sJdRUWmUMPSchKtchb6S7P8j+dcWYDyh04JW5YFg6RfqTbQajZwdX6C04tPTgPLpi+giWFsvVuDjzUEkqpASQj+ELZ+3MabsIxKSUHlrouZxSQ2kosY9HqOz/utW9Rum7itV3B+/DeulLLTG64u0/BPzGJtdQk2SYLZR2RefMVw9TbLh3VqKin84Ukc/PYBv+7vIOz3oV6ro1Epk5TrCAaDqJbyaA9MskwPOaWAgW4iOjlDcp+FRdVmjS65noRMkTCPU2YIx+fx+4c/sPvTOiTBRKXSQI1wKrcdhIMSimoeA+oner+JXL78qj7gigCWuBQIEPvkQ1GiRAOIkLQl1uGJHcs0IUoS+h1SxdU1ujpFa2lQsgWQqqmRtaiBNZyOaJk6VOolbJ7Zg1WVYQfpiITvrxmmaZhkNwnNagnXmRx0WnUMrQ1FKVLrdD/c9QAKSKTmNx2fQJo69MnxEfxmF+2+jvhCBBMz89iLTlDcpBLbwNnhn7gzwtjd20FItGi1OEeNpB+fniUybp3VREkksDobRzwawk1ZwdGJjUGzBp1Wg7k3UUwtLuCX+JxDkKF1cPTXIezoNN6/33KWSLcUuCPAEb+I5MYm2r0TknmBZgTEEnOO52VJhD8ac+Zg6QgFZfT0IKLRCO0LSihW7xCOTWJ752d0SmmkMiryuSzmZ3axub0F7eQcRSXnvD+1sIr1lUVSlIhYLHZPgF8ghcgwA2GEQ0HHNvSwqyG85n+EmFwtQ0en23U2QpFo1NnosF3hl4NZgq3XIlnEpsZp0sGWRImSerhmu0lmG7Y/MDQNXdYQBdEhja0A7P7jIEuwTcVnzMf57z97FQH3n6POTkQIAu3UqDN/Geb3h/P5DQePMCnp52S6xhzyomsLPOJR0hTokwI93nR35uD9T2R+IwKXq8A3UD10mxPgoWKNJFSugJHQ6iFQrgAPFWskoXIFjIRWD4FyBXioWCMJlStgJLR6CJQrwEPFGkmoXAEjodVDoFwBHirWSELlChgJrR4C5QrwULFGEipXwEho9RDoPwJqK/yQ8YYSAAAAAElFTkSuQmCC" alt="...">
                </a>
                <div class="media-body">
                  <h4 class="media-heading"><a data-bind="attr: { href: '<c:url value="/files/download/"/>'+idAsString }" target="_blank"><span data-bind="text: filename"></span></a></h4>
                  <span data-bind="text: 'Lorem ipsum ...'"></span>
                  <p><span data-bind="text: uploadDate | smartdate "></span></p>
                </div>
              </li>
            </ul>
          </div>
          
          <!-- 
            TAB: HISTORY
            -->
          <div id="history" class="tab-pane fade">
            <!-- TODO -->
            This option is only available if you use our firefox plugin!
          </div>
          
        </div>
      </div>
    </div>
</body>
</html>
