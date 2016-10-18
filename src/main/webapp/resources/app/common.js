var Header = React.createClass({
  render: function() {
	  return <h1> Header {this.props.name}</h1>;
}
});

var Main = React.createClass({
	  render: function() {
		  return <h1> Main {this.props.name}</h1>;
}
});

var Footer = React.createClass({
	  render: function() {
	    return <h1> Footer {this.props.name}</h1>;
	  }
	});



ReactDOM.render(
		  <Header name="header" />,
		  document.getElementById('header')
		);

		ReactDOM.render(
		  <Main name="lister"/>,
		  document.getElementById('main')
		);

		ReactDOM.render(
		  <Footer name="footer"/>,
		  document.getElementById('footer')
		);