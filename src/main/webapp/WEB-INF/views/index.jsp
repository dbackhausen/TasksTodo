<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html manifest="/cache.manifest">
  <head>
    <title>Tasks Todo</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="description" content="">
    <link rel="stylesheet" href="<c:url value="/resources/css/normalize.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-theme.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/jquery.fileupload.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
    <script src="<c:url value="/resources/js/jquery-1.10.2.min.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery.ui.widget.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery.iframe-transport.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery.fileupload.js"/>"></script>
    <script src="<c:url value="/resources/js/respond.min.js"/>"></script>
    <script src="<c:url value="/resources/js/modernizr-2.6.2.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap-paginator.js"/>"></script>
    <script src="<c:url value="/resources/js/knockout-3.0.0.js"/>"></script>
    <script src="<c:url value="/resources/js/knockout.punches.min.js"/>"></script>
    <script src="<c:url value="/resources/js/tasks.js"/>"></script>
  </head>
  <body>
    <nav class="navbar navbar-default" role="navigation">
      <div class="navbar-header">
        <a class="navbar-brand" href="">Tasks Todo</a>
      </div>

      <p class="navbar-text navbar-right">Signed in as Daniel Backhausen</p>
    </nav>
  
    <div id="tasks" class="row">
      <div class="col-md-3">
        <h3>Inbox</h3>
        <ul id="task-list" data-bind="foreach: tasks" class="task-list">
          <li data-bind="attr: { id: idAsString }">
            <a data-bind="click: $parent.selectTask"><span data-bind="text: title"></span></a>
          </li>
        </ul>        
      </div>

      <div class="col-md-9" data-bind="if: selectedTask() != null">
        <!-- Navigation tabs -->
        <ul class="nav nav-tabs">
          <li id="tab-details" class="active"><a href="#details" data-toggle="tab"><i class="glyphicon glyphicon-th-large"></i> <spring:message code="tab.task.details" /></a></li>
          <li id="tab-notes"><a href="#notes" data-toggle="tab"><i class="glyphicon glyphicon-comment"></i> <spring:message code="tab.task.notes" /></a></li>
          <li id="tab-links"><a href="#links" data-toggle="tab"><i class="glyphicon glyphicon-link"></i> <spring:message code="tab.task.links" /></a></li>
          <li id="tab-files"><a href="#files" data-toggle="tab"><i class="glyphicon glyphicon-file"></i> <spring:message code="tab.task.files" /></a></li>
          <li id="tab-history"><a href="#history" data-toggle="tab"><i class="glyphicon glyphicon-hdd"></i> <spring:message code="tab.task.history" /></a></li>
        </ul>
          
        <!-- Tab panes -->
        <div class="tab-content">
        
          <!-- 
            TAB: DETAILS
            -->
          <div id="details" class="tab-pane fade in active">
            <!-- TODO -->
            <p><label for="task-details-title">Title</label>
            <span id="task-details-title" data-bind="text: selectedTask().title"></span></p>
            <p><label for="task-details-title">Description</label>
            <span data-bind="text: selectedTask().description"></span></p>
            <p><label for="task-details-title">Due date</label>
            <span data-bind="text: selectedTask().dueDate"></span></p>
            <p><label for="task-details-title">Completed date</label>
            <span data-bind="text: selectedTask().completedDate"></span></p>
            <p><label for="task-details-title">Reminder date</label>
            <span data-bind="text: selectedTask().reminderDate"></span></p>
            <p><label for="task-details-title">Urgency</label>
            <span data-bind="text: selectedTask().urgency"></span></p>
            <p><label for="task-details-title">Priority</label>
            <span data-bind="text: selectedTask().priority"></span></p>
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
                  <p><span data-bind="text: created"></span></p>
                  <button data-bind="click: $parent.editNote" class="btn btn-default btn-sm"><spring:message code="button.edit" /></button>
                  <button data-bind="click: $parent.deleteNote" class="btn btn-default btn-sm"><spring:message code="button.delete" /></button>
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
                    <span data-bind="text: description" class="inline-view"></span>
                    <p><span data-bind="text: created"></span></p>
                    <button data-bind="click: $parent.editLink" class="btn btn-default btn-sm"><spring:message code="button.edit" /></button>
                    <button data-bind="click: $parent.deleteLink" class="btn btn-default btn-sm"><spring:message code="button.delete" /></button>
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
          
<span class="btn btn-success fileinput-button">
  <i class="glyphicon glyphicon-plus"></i>
  <span>Add files...</span>
  <input id="fileupload" type="file" name="files[]" multiple>
</span>
<button data-bind="click: addFile" class="btn btn-primary">Upload</button>
              
            <hr />
            <ul data-bind="foreach: files" class="media-list">
              <li data-bind="attr: { id: idAsString }" class="media">
                <a class="pull-left" href="#">
                  <img class="media-object" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAFQElEQVR4Ae1aa1PbRhQ9smT5TbB5QwAXCDAtnQBDO+1k+v+/tZRHwIBxbNmyjV/gt6xX74owBFI3RNQfNN5lNJZW0tG9555zdz8g1Ot1G2M8fGOcu5M6J4ArYMwZ4BYYcwGAK4ArYMwZ4BYYcwHwJsgtwC0w5gxwC4y5APgqwC3ALTDmDHALjLkA+CrALfA6Cwjw+XywbRsWHexcEJ4iCjTxcDy9M+SKnvf5BMK0HFyG+Xzc4+Grbz1/7iXX0kse+vdnKDGYULNZKMUbmLaAxMwCflh9C1liCVCAFHy7XkYmp8Lnj2Bjcw2y+HVCD/gsMcvQkMlmULqpQxD9mF1YxsrbeYiw6Q8OOdWignyphmAkgY2N5Vf5eHg0D1EN+WWFKefSOD49R6unQe93cHl2jLO0wjK/L4/Rx9VFCkpBhVqqwrTup1miD2p5PGeEWshefMRZ6hp9w0Kv1cDp0SEyatUhkxGqd5tIpVIoqCrKlTqR8kxyQ+IdNu1SAVRhU0O+UIQtR7Cze4CY0EdWUSHJopOoXxSgfsrg5raHgEyfoeAFIkZr35EiCrBFGSvJNVjtChSqphSIYWVpEgUiSo5OYf9gD3ariny5CsnHLEYQAhF0fYmWZkP2iw7esMReOu+OAKqgqfXQG+iQpCBulDQUTcfE1CxWV5YoYKDfbuCS1JAgCYudG9T6lAH5IhAKoU+VzVeb6GsG7G4Vaq2Ndzv7sAddaCQTf1BwlDAwbCTmF7EyPwOR+sJdRUWmUMPSchKtchb6S7P8j+dcWYDyh04JW5YFg6RfqTbQajZwdX6C04tPTgPLpi+giWFsvVuDjzUEkqpASQj+ELZ+3MabsIxKSUHlrouZxSQ2kosY9HqOz/utW9Rum7itV3B+/DeulLLTG64u0/BPzGJtdQk2SYLZR2RefMVw9TbLh3VqKin84Ukc/PYBv+7vIOz3oV6ro1Epk5TrCAaDqJbyaA9MskwPOaWAgW4iOjlDcp+FRdVmjS65noRMkTCPU2YIx+fx+4c/sPvTOiTBRKXSQI1wKrcdhIMSimoeA+oner+JXL78qj7gigCWuBQIEPvkQ1GiRAOIkLQl1uGJHcs0IUoS+h1SxdU1ujpFa2lQsgWQqqmRtaiBNZyOaJk6VOolbJ7Zg1WVYQfpiITvrxmmaZhkNwnNagnXmRx0WnUMrQ1FKVLrdD/c9QAKSKTmNx2fQJo69MnxEfxmF+2+jvhCBBMz89iLTlDcpBLbwNnhn7gzwtjd20FItGi1OEeNpB+fniUybp3VREkksDobRzwawk1ZwdGJjUGzBp1Wg7k3UUwtLuCX+JxDkKF1cPTXIezoNN6/33KWSLcUuCPAEb+I5MYm2r0TknmBZgTEEnOO52VJhD8ac+Zg6QgFZfT0IKLRCO0LSihW7xCOTWJ752d0SmmkMiryuSzmZ3axub0F7eQcRSXnvD+1sIr1lUVSlIhYLHZPgF8ghcgwA2GEQ0HHNvSwqyG85n+EmFwtQ0en23U2QpFo1NnosF3hl4NZgq3XIlnEpsZp0sGWRImSerhmu0lmG7Y/MDQNXdYQBdEhja0A7P7jIEuwTcVnzMf57z97FQH3n6POTkQIAu3UqDN/Geb3h/P5DQePMCnp52S6xhzyomsLPOJR0hTokwI93nR35uD9T2R+IwKXq8A3UD10mxPgoWKNJFSugJHQ6iFQrgAPFWskoXIFjIRWD4FyBXioWCMJlStgJLR6CJQrwEPFGkmoXAEjodVDoFwBHirWSELlChgJrR4C5QrwULFGEipXwEho9RDoPwJqK/yQ8YYSAAAAAElFTkSuQmCC" alt="...">
                </a>
                <div class="media-body">
                  <h4 class="media-heading"><a data-bind="attr: { href: '<c:url value="/files/download/"/>'+idAsString }" target="_blank"><span data-bind="text: filename"></span></a></h4>
                  <span data-bind="text: 'Lorem ipsum ...'"></span>
                  <p><span data-bind="text: uploadDate"></span></p>
                </div>
              </li>
            </ul>
          </div>
          
          <!-- 
            TAB: HISTORY
            -->
          <div id="history" class="tab-pane fade">
            <!-- TODO -->
          </div>
          
        </div>
      </div>
    </div>
</body>
</html>
